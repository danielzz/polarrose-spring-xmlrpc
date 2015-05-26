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

import com.polarrose.xmlrpc.XmlRpcServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.support.WebContentGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Stefan Arentz <stefan@polarrose.com>
 */

class XmlRpcServiceExporter extends RemoteExporter implements HttpRequestHandler, InitializingBean
{
    /**
     *
     */

    private XmlRpcServer server;

    /**
     *
     */

    private String namespace;

    /**
     * @param namespace
     */

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    /**
     * @see org.springframework.web.HttpRequestHandler#handleRequest(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
     */

    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        // We only handle POST methods

        if (!WebContentGenerator.METHOD_POST.equals(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod());
        }

        // Handle the request

        response.setContentType("text/xml");

        server.execute(request.getInputStream(), response.getWriter());
        response.getWriter().flush();
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */

    public void afterPropertiesSet()
        throws Exception
    {
        checkService();
        checkServiceInterface();

        // Setup the namespace or use the service interface's simple name if not set

        if (namespace == null) {
            namespace = getServiceInterface().getSimpleName();
        }

        // Setup the XML-RPC server

        server = new XmlRpcServer();
        server.addInvocationHandler(namespace, getProxyForService());
    }
}