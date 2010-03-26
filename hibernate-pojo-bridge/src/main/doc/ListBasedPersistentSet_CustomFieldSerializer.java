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



import java.util.ArrayList;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.core.java.util.ArrayList_CustomFieldSerializer;

/**
 * @author Norbert Sándor
 */
public class ListBasedPersistentSet_CustomFieldSerializer {
    @SuppressWarnings("unchecked")
    public static void deserialize(SerializationStreamReader streamReader, ListBasedPersistentSet<?> instance)
            throws SerializationException {
        ArrayList<?> list = new ArrayList<Object>();
        ArrayList_CustomFieldSerializer.deserialize(streamReader, list);
        instance.setList((ArrayList) list);

        instance.setDirty(streamReader.readBoolean());
    }

    public static void serialize(SerializationStreamWriter streamWriter, ListBasedPersistentSet<?> instance)
            throws SerializationException {
        ArrayList_CustomFieldSerializer.serialize(streamWriter, instance.getList());
        streamWriter.writeBoolean(instance.isDirty());
    }
}
