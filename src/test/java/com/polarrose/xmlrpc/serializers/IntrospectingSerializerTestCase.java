package com.polarrose.xmlrpc.serializers;

import java.io.IOException;
import java.io.StringWriter;

import junit.framework.TestCase;

import com.polarrose.xmlrpc.XmlRpcSerializer;

public class IntrospectingSerializerTestCase extends TestCase
{
    private String serializeObject(Object object) throws IOException
    {
        XmlRpcSerializer xmlRpcSerializer = new XmlRpcSerializer();
        StringWriter writer = new StringWriter();

        IntrospectingSerializer serializer = new IntrospectingSerializer();
        serializer.serialize(object, writer, xmlRpcSerializer);

        return writer.toString();
    }

    //

    public void testSerializeSimpleObject() throws IOException
    {
        assertEquals(
            "<struct><member><name>firstName</name><value><string>Stefan</string></value></member><member><name>lastName</name><value><string>Arentz</string></value></member></struct>",
            serializeObject(new Person("Stefan", "Arentz"))
        );
    }

    public void testSerializeSubclass() throws IOException
    {
        assertEquals(
            "<struct><member><name>crazyness</name><value><i4>10</i4></value></member><member><name>firstName</name><value><string>Joep</string></value></member><member><name>lastName</name><value><string>Meloen</string></value></member></struct>",
            serializeObject(new CrazyPerson("Joep", "Meloen", 10))
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

        Person(String firstName, String lastName)
        {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    class CrazyPerson extends Person
    {
        private final int crazyness;

        public int getCrazyness()
        {
            return crazyness;
        }

        CrazyPerson(String firstName, String lastName, int crazyness)
        {
            super(firstName, lastName);
            this.crazyness = crazyness;
        }
    }
}
