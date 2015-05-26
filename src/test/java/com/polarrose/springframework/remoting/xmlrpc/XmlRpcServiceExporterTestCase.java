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

import com.polarrose.xmlrpc.HelloService;
import com.polarrose.xmlrpc.XmlRpcProxy;
import junit.framework.TestCase;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import java.net.MalformedURLException;
import java.net.URL;

public class XmlRpcServiceExporterTestCase extends TestCase
{
    private Server server;
    private Connector connector;

    @Override
    protected void setUp() throws Exception
    {
        connector = new SocketConnector();
        connector.setPort(0);

        server = new Server();
        server.setConnectors(new Connector[]{connector});

        ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

        ServletHandler servletHandler = root.getServletHandler();
        root.start();

        ServletContext servletContext = servletHandler.getServletContext();

        GenericWebApplicationContext genericWebContext = new GenericWebApplicationContext();
        genericWebContext.setServletContext(servletContext);
        genericWebContext.refresh();

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, genericWebContext);

        ServletHolder servletHolder = new ServletHolder(new DispatcherServlet());
        servletHolder.setName("remoting");
        servletHolder.setInitParameter("contextConfigLocation", "classpath:com/polarrose/springframework/remoting/xmlrpc/XmlRpcServiceExporterTestCase-spring.xml");
        servletHandler.addServlet(servletHolder);

        ServletMapping servletMapping = new ServletMapping();
        servletMapping.setServletName("remoting");
        servletMapping.setPathSpec("/*");
        servletHandler.addServletMapping(servletMapping);

        server.start();
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

    public void testXmlRpcExporter()
    {
        HelloService helloService = ( HelloService ) XmlRpcProxy.createProxy(
            getEndpointUrl(),
            "helloService",
            new Class[] { HelloService.class },
            false
        );

        String message = helloService.sayHello("Stefan");
        assertEquals("Hello, Stefan", message);
    }
}
