package com.polarrose.xmlrpc;

/**
 * Created by IntelliJ IDEA.
 * User: stefan
 * Date: Mar 6, 2007
 * Time: 7:29:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class MathServiceImpl implements MathService
{
    public double execute(MathOperation operation) throws Exception
    {
        if (operation.getOp().equals("add")) {
            return operation.getA() + operation.getB();
        }

        throw new Exception("Unsupported math service operation");
    }
}
