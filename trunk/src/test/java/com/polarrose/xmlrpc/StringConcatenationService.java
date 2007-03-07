package com.polarrose.xmlrpc;

import java.util.List;

public interface StringConcatenationService
{
    String concatStringList(List<String> strings);
    String concatStringArray(String[] strings);
    String concatStringVarArgs(String ... strings);
}

