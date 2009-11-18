/*
 * Copyright 2009 Norbert Sándor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.erinors.hpb.test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

import ch.qos.logback.core.WriterAppender;

public class SqlAppender<E> extends WriterAppender<E>
{
    private static SqlAppender<?> INSTANCE;

    public static SqlAppender<?> get()
    {
        return INSTANCE;
    }

    private StringWriter writer = new StringWriter();

    @Override
    public void setName(String name)
    {
        super.setName(name);
        INSTANCE = this;
    }

    @Override
    public void start()
    {
        setWriter(writer);
        super.start();
    }

    public List<String> getSql()
    {
        List<String> executedSql = new ArrayList<String>();

        String currentSql = null;
        List<String> currentArguments = new ArrayList<String>();
        int argumentCount = 0;
        for (String line : writer.toString().split("\\r\\n"))
        {
            if (line.startsWith("binding "))
            {
                assert currentSql != null;
                assert argumentCount > 0;

                Matcher matcher = Pattern.compile("binding (.*) to parameter: (\\d+)").matcher(line);
                if (matcher.matches())
                {
                    assert Integer.parseInt(matcher.group(2)) == currentArguments.size() - 1;
                    currentArguments.add(matcher.group(1));
                }
                else
                {
                    Assert.fail("Internal error!");
                }

                if (argumentCount == currentArguments.size())
                {
                    String sql = currentSql;
                    for (String argument : currentArguments)
                    {
                        sql = sql.replaceFirst("\\?", argument);
                    }

                    executedSql.add(sql);
                    currentArguments.clear();
                }
            }
            else if (line.startsWith("returning "))
            {
                Assert.fail();
            }
            else
            {
                currentSql = line;
                argumentCount = 0;
                int currentIndex = -1;
                while ((currentIndex = currentSql.indexOf("?", currentIndex + 1)) != -1)
                {
                    argumentCount++;
                }

                currentArguments.clear();
            }
        }

        clearSql();

        return executedSql;
    }

    @Override
    protected void writerWrite(String s, boolean flush) throws IOException
    {
        if (!s.startsWith("returning "))
        {
            super.writerWrite(s, flush);
        }
    }

    public void clearSql()
    {
        writer.getBuffer().setLength(0);
    }
}
// TODO what happens if the query itself contains a ? as static text?
