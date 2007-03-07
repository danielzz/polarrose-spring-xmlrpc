// XmlRpcServletTestCase.java

package com.polarrose.xmlrpc;

import junit.framework.TestCase;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class XmlRpcServletTestCase extends TestCase
{
    private Server server;
    private Connector connector;

    private XmlRpcServlet xmlRpcServlet;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        connector = new SocketConnector();
        connector.setPort(0);

        xmlRpcServlet = new XmlRpcServlet();

        server = new Server();
        server.setConnectors(new Connector[]{connector});
        Context context = new Context(server, "/", Context.SESSIONS);
        context.addServlet(new ServletHolder(xmlRpcServlet), "/RPC2");
        server.start();
    }

    @Override
    protected void tearDown() throws Exception
    {
        //server.stop();
        super.tearDown();
    }

    private URL getEndpointUrl() throws MalformedURLException
    {
        return new URL("http", "127.0.0.1", connector.getLocalPort(), "/RPC2");
    }

    public void testXmlRpcRequest() throws MalformedURLException
    {
        xmlRpcServlet.getXmlRpcServer().addInvocationHandler("HelloService", new HelloServiceImpl());

        HelloService helloService = ( HelloService ) XmlRpcProxy.createProxy(
            getEndpointUrl(),
            new Class[] { HelloService.class },
            false
        );

        String message = helloService.sayHello("Stefan");
        assertEquals("Hello, Stefan", message);
    }

//    public void testMoreComplexXmlRpcRequest() throws MalformedURLException, Exception
//    {
//        xmlRpcServlet.getXmlRpcServer().addInvocationHandler("MathService", new MathServiceImpl());
//
//        MathService mathService = ( MathService ) XmlRpcProxy.createProxy(
//            getEndpointUrl(),
//            new Class[] { MathService.class },
//            false
//        );
//
//        double result = mathService.execute(new MathOperation("add", 1.12, 2.02));
//        assertEquals(3.14, result);
//    }

    public void testMoreComplexXmlRpcRequest2() throws MalformedURLException, Exception
    {
        xmlRpcServlet.getXmlRpcServer().addInvocationHandler("StringConcatenationService", new StringConcatenationServiceImpl());

        StringConcatenationService stringConcatenationService = ( StringConcatenationService ) XmlRpcProxy.createProxy(
            getEndpointUrl(),
            new Class[] { StringConcatenationService.class },
            false
        );

        assertEquals(
            "HalloBoppers!",
            stringConcatenationService.concatStringList(Arrays.asList("Hallo", "Boppers", "!"))
        );

        assertEquals(
            "HalloBoppers!",
            stringConcatenationService.concatStringArray(new String[] { "Hallo", "Boppers", "!" })
        );

        assertEquals(
            "HalloBoppers!",
            stringConcatenationService.concatStringVarArgs("Hallo", "Boppers", "!")
        );
    }

    public void testComplexService() throws MalformedURLException, Exception
    {
        xmlRpcServlet.getXmlRpcServer().addInvocationHandler("ComplexService", new ComplexServiceImpl());

        ComplexService complexService = ( ComplexService ) XmlRpcProxy.createProxy(
            getEndpointUrl(),
            new Class[] { ComplexService.class },
            false
        );

        assertEquals(
            "BoeBlahBoeBlahBoeBlah",
            complexService.concatManyTimes(3, "Boe", "Blah")
        );

        assertEquals(
            15,
            complexService.addManyInts(1, 2, 3, 4, 5)
        );
    }
}
