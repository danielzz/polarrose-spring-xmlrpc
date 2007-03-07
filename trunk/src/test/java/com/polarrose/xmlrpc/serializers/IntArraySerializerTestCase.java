// IntArraySerializerTestCase.java

package com.polarrose.xmlrpc.serializers;

import java.io.IOException;
import java.io.StringWriter;

import junit.framework.TestCase;

import com.polarrose.xmlrpc.XmlRpcSerializer;

public class IntArraySerializerTestCase extends TestCase
{
    private String serializeIntArray(int[] array) throws IOException
    {
        XmlRpcSerializer xmlRpcSerializer = new XmlRpcSerializer();
        StringWriter writer = new StringWriter();

        IntArraySerializer intArraySerializer = new IntArraySerializer();
        intArraySerializer.serialize(array, writer, xmlRpcSerializer);

        return writer.toString();
    }

    //

    public void testIntArraySerializer() throws IOException
    {
        assertEquals(
            "<array><data><value><i4>-1</i4></value><value><i4>0</i4></value><value><i4>1</i4></value></data></array>",
            serializeIntArray(new int[] {-1,0,1})
        );
    }

    public void testIntArraySerializerWithEmptyArray() throws IOException
    {
        assertEquals(
            "<array><data></data></array>",
            serializeIntArray(new int[] {})
        );
    }
}
