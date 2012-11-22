package com.erinors.hpb.tests.integration.case0006;

public class WithInnerClass
{
    public class Inner
    {
        private int i = 1;

        private Object me;

        public Inner()
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

    private Inner inner = new Inner();

    public Inner getInner()
    {
        return inner;
    }
}
