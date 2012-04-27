package com.erinors.hpb.tests.integration.case0006;

public class WithNestedClass
{
    public static class Nested
    {
        private int i = 1;

        private Object me;

        public Nested()
        {
            me = this;
        }

        public int getI()
        {
            return i;
        }

        public void setI(int i)
        {
            this.i = i;
        }

        public Object getMe()
        {
            return me;
        }
    }

    private Nested inner = new Nested();

    public Nested getInner()
    {
        return inner;
    }
}
