package com.polarrose.xmlrpc;

/**
 * Created by IntelliJ IDEA.
 * User: stefan
 * Date: Mar 6, 2007
 * Time: 7:26:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MathService
{
    double execute(MathOperation operation) throws Exception;
}
