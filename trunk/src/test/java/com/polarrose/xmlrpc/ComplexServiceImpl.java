package com.polarrose.xmlrpc;

import java.util.List;
import java.util.Arrays;

public class ComplexServiceImpl implements ComplexService
{
    public String concatManyTimes(int n, String... strings)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++)
        {
            for (String string : strings) {
                sb.append(string);
            }
        }

        return sb.toString();
    }

    public int addManyInts(int ... integers)
    {
        int result = 0;

        for (int i : integers) {
            result += i;
        }

        return result;
    }

    public List<String> returnManyStringsAsList()
    {
        return Arrays.asList("Hallo", "Boppers");
    }

    public String[] returnManyStringsAsArray()
    {
        return new String[] { "Amsterdam", "Toronto" };
    }

    public List<String> returnListWithNullItem()
    {
        return Arrays.asList("Hallo", null, "Boppers");
    }
}
