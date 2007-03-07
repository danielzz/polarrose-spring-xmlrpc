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

import org.springframework.beans.factory.FactoryBean;
import org.springframework.aop.framework.ProxyFactory;

/**
 * Factory bean for XmlRpc proxies. Behaves like the proxied service when
 * used as bean reference, exposing the specified service interface.
 */

public class XmlRpcProxyFactoryBean extends XmlRpcClientInterceptor implements FactoryBean
{
    private Object serviceProxy;

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */

    public Object getObject() throws Exception
    {
        return serviceProxy;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */

    public Class getObjectType()
    {
        return getServiceInterface();
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */

    public boolean isSingleton()
    {
        return true;
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */

    public void afterPropertiesSet()
    {
        super.afterPropertiesSet();
        this.serviceProxy = ProxyFactory.getProxy(getServiceInterface(), this);
    }
}
