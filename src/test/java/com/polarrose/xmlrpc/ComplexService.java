package com.polarrose.xmlrpc;

public interface ComplexService
{
    String concatManyTimes(int n, String ... strings);

    int addManyInts(int ... i);
}
