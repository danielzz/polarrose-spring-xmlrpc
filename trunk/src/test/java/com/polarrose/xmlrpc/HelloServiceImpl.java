// HelloServiceImpl.java

package com.polarrose.xmlrpc;

public class HelloServiceImpl implements HelloService
{
    public String sayHello(String name)
    {
        return "Hello, " + name;
    }
}
