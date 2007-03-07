/*
 * Copyright (c) 2006-2007 Polar Rose / http://www.polarrose.com
 *
 *  This library is free software; you can redistribute it and/or modify it under the terms
 *  of the GNU Lesser General Public License as published by the Free Software Foundation;
 *  either version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License along with this
 *  library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *  Boston, MA  02111-1307  USA
 */

package com.polarrose.springframework.remoting.xmlrpc;

import javax.servlet.ServletContext;
import java.net.URL;
import java.net.MalformedURLException;

import junit.framework.TestCase;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.servlet.ServletMapping;
import org.mortbay.jetty.bio.SocketConnector;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.polarrose.xmlrpc.HelloService;

public class XmlRpcProxyFactoryBeanTestCase extends TestCase
{
    private Server server;
    private Connector connector;
    DispatcherServlet dispatcherServlet;

    @Override
    protected void setUp() throws Exception
    {
        connector = new SocketConnector();
        connector.setPort(10927);

        server = new Server();
        server.setConnectors(new Connector[]{connector});
        server.start();

        Context root = new Context(server, "/", Context.SESSIONS);

        ServletHandler servletHandler = root.getServletHandler();

        ServletContext servletContext = servletHandler.getServletContext();

        GenericWebApplicationContext genericWebContext = new GenericWebApplicationContext();
        genericWebContext.setServletContext(servletContext);
        genericWebContext.refresh();

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, genericWebContext);

        dispatcherServlet = new DispatcherServlet();
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        servletHolder.setName("remoting");
        servletHolder.setInitParameter("contextConfigLocation", "classpath:com/polarrose/springframework/remoting/xmlrpc/XmlRpcProxyFactoryBeanTestCase-spring.xml");
        servletHandler.addServlet(servletHolder);

        ServletMapping servletMapping = new ServletMapping();
        servletMapping.setServletName("remoting");
        servletMapping.setPathSpec("/*");
        servletHandler.addServletMapping(servletMapping);
    }

    @Override
    protected void tearDown() throws Exception
    {
        server.stop();
    }

    private URL getEndpointUrl()
    {
        try {
            return new URL("http", "127.0.0.1", connector.getLocalPort(), "/RPC2");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    //

    public void testXmlRpcProxyFactoryBean()
    {
        HelloService importedHelloService = (HelloService) dispatcherServlet.getWebApplicationContext().getBean("importedHelloService");
        assertNotNull(importedHelloService);

        String message = importedHelloService.sayHello("Stefan");
        assertEquals("Hello, Stefan", message);
    }
}
