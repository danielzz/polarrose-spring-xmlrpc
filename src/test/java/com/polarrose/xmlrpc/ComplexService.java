package com.polarrose.xmlrpc;

import java.util.List;

public interface ComplexService
{
    String concatManyTimes(int n, String ... strings);

    int addManyInts(int ... i);

    List<String> returnManyStringsAsList();

    String[] returnManyStringsAsArray();

    List<String> returnListWithNullItem();
}
