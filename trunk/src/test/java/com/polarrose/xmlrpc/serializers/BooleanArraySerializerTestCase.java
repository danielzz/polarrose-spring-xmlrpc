// BooleanArraySerializerTestCase.java

package com.polarrose.xmlrpc.serializers;

import java.io.StringWriter;
import java.io.IOException;

import junit.framework.TestCase;

import com.polarrose.xmlrpc.XmlRpcSerializer;

public class BooleanArraySerializerTestCase extends TestCase
{
    private String serializeBooleanArray(boolean[] array) throws IOException
    {
        XmlRpcSerializer xmlRpcSerializer = new XmlRpcSerializer();
        StringWriter writer = new StringWriter();

        BooleanArraySerializer serializer = new BooleanArraySerializer();
        serializer.serialize(array, writer, xmlRpcSerializer);

        return writer.toString();
    }

    //

    public void testBooleanArraySerializer() throws IOException
    {
        assertEquals(
            "<array><data><value><boolean>1</boolean></value><value><boolean>0</boolean></value></data></array>",
            serializeBooleanArray(new boolean[] {true,false})
        );
    }

    public void testBooleanArraySerializerWithEmptyArray() throws IOException
    {
        assertEquals(
            "<array><data></data></array>",
            serializeBooleanArray(new boolean[] {})
        );
    }
}
