package com.polarrose.xmlrpc;

import java.util.List;

public class StringConcatenationServiceImpl implements StringConcatenationService
{
    public String concatStringList(List<String> strings)
    {
        StringBuilder sb = new StringBuilder();

        for (String string : strings) {
            sb.append(string);
        }

        return sb.toString();
    }

    public String concatStringArray(String[] strings)
    {
        StringBuilder sb = new StringBuilder();

        for (String string : strings) {
            sb.append(string);
        }

        return sb.toString();
    }

    public String concatStringVarArgs(String... strings)
    {
        StringBuilder sb = new StringBuilder();

        for (String string : strings) {
            sb.append(string);
        }

        return sb.toString();
    }
}
