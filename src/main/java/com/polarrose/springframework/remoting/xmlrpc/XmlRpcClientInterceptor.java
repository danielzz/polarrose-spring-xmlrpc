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

import com.polarrose.xmlrpc.XmlRpcProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Interceptor for accessing an XmlRpc service.
 */

public class XmlRpcClientInterceptor extends UrlBasedRemoteAccessor implements MethodInterceptor
{
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
     *
     */

    private Object proxy;

    /**
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */

    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        if (this.proxy == null) {
            throw new IllegalStateException("XmlRpcClientInterceptor is not properly initialized - invoke 'prepare' before attempting any operations");
        }

        try {
            return invocation.getMethod().invoke(this.proxy, invocation.getArguments());
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Throwable ex) {
            throw new RemoteProxyFailureException("Failed to invoke Hessian proxy for remote service [" + getServiceUrl() + "]", ex);
        }
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */

    @Override
    public void afterPropertiesSet()
    {
        super.afterPropertiesSet();
        prepare();
    }

    /**
     * Initialize the Hessian proxy for this interceptor.
     * @throws org.springframework.remoting.RemoteLookupFailureException if the service URL is invalid
     */

    public void prepare() throws RemoteLookupFailureException
    {
        try {
            this.proxy = XmlRpcProxy.createProxy(new URL(getServiceUrl()), namespace, new Class[] { getServiceInterface() }, false);
        } catch (MalformedURLException ex) {
            throw new RemoteLookupFailureException("Service URL [" + getServiceUrl() + "] is invalid", ex);
        }
    }
}
