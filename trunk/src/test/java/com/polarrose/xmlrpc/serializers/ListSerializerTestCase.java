package com.polarrose.xmlrpc.serializers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import junit.framework.TestCase;

import com.polarrose.xmlrpc.XmlRpcSerializer;

public class ListSerializerTestCase extends TestCase
{
    private String serializeList(List list) throws IOException
    {
        XmlRpcSerializer xmlRpcSerializer = new XmlRpcSerializer();
        StringWriter writer = new StringWriter();

        ListSerializer serializer = new ListSerializer();
        serializer.serialize(list, writer, xmlRpcSerializer);

        return writer.toString();
    }

    //

    public void testListSerializer() throws IOException
    {
        assertEquals(
            "<array><data><value><i4>-1</i4></value><value><i4>0</i4></value><value><i4>1</i4></value></data></array>",
            serializeList(Arrays.asList(-1, 0, 1))
        );
    }

    public void testListSerializerWithEmptyArray() throws IOException
    {
        assertEquals(
            "<array><data></data></array>",
            serializeList(Arrays.asList())
        );
    }

    public void testGenericListSerializer() throws IOException
    {
        List<Integer> list = new ArrayList<Integer>();
        list.add(-1);
        list.add(0);
        list.add(1);

        assertEquals(
            "<array><data><value><i4>-1</i4></value><value><i4>0</i4></value><value><i4>1</i4></value></data></array>",
            serializeList(list)
        );
    }

    public void testListWithObjectsSerializer() throws IOException
    {
        List<Person> list = new ArrayList<Person>();
        list.add(new Person("Stefan", "Arentz"));
        list.add(new Person("Bill", "Gates"));

        assertEquals(
            "<array><data><value><struct><member><name>firstName</name><value><string>Stefan</string></value></member><member><name>lastName</name><value><string>Arentz</string></value></member></struct></value><value><struct><member><name>firstName</name><value><string>Bill</string></value></member><member><name>lastName</name><value><string>Gates</string></value></member></struct></value></data></array>",
            serializeList(list)
        );
    }

    class Person
    {
        private final String firstName;

        public String getFirstName()
        {
            return firstName;
        }

        private final String lastName;

        public String getLastName()
        {
            return lastName;
        }

        public Person(String firstName, String lastName)
        {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
