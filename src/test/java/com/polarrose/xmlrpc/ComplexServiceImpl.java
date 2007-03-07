package com.polarrose.xmlrpc;

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
}
