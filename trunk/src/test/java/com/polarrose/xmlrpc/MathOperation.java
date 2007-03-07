package com.polarrose.xmlrpc;

public class MathOperation
{
    private double a;

    public double getA()
    {
        return a;
    }

    public void setA(double a)
    {
        this.a = a;
    }

    private double b;

    public double getB()
    {
        return b;
    }

    public void setB(double b)
    {
        this.b = b;
    }

    private String op;

    public String getOp()
    {
        return op;
    }

    public void setOp(String op)
    {
        this.op = op;
    }

    public MathOperation(String op, double a, double b)
    {
        this.op = op;
        this.a = a;
        this.b = b;
    }

    public MathOperation()
    {
    }
}
