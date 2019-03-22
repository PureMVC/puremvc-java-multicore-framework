//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the PureMVC Proxy class.
 *
 * @see org.puremvc.java.multicore.interfaces.IProxy IProxy
 * @see Proxy Proxy
 */
public class ProxyTest {

    /**
     * Tests getting the name using Proxy class accessor method. Setting can only be done in constructor.
     */
    @Test
    public void testNameAccessory() {
        Proxy proxy = new Proxy("TestProxy");

        // test assertions
        Assertions.assertTrue(proxy.getProxyName() == "TestProxy", "Expecting proxy.getProxyName == 'TestProxy'");
    }

    /**
     * Tests setting and getting the data using Proxy class accessor methods.
     */
    @Test
    public void testDataAccessors() {
        // Create a new Proxy and use accessors to set the data
        Proxy proxy = new Proxy("colors");
        proxy.setData(new String[]{"red", "green", "blue"});

        String[] data = (String[]) proxy.getData();

        // test assertions
        Assertions.assertTrue(data.length == 3, "Expecting data.length == 3");
        Assertions.assertTrue(data[0] == "red", "Expecting data[0] == 'red'");
        Assertions.assertTrue(data[1] == "green", "Expecting data[1] == 'green'");
        Assertions.assertTrue(data[2] == "blue", "Expecting data[2] == 'blue'");
    }

    /**
     * Tests setting the name and body using the Notification class Constructor.
     */
    @Test
    public void testConstructor() {
        // Create a new Proxy using the Constructor to set the name and data
        Proxy proxy = new Proxy("colors", new String[] {"red", "green", "blue"});
        String[] data = (String[]) proxy.getData();

        // test assertions
        Assertions.assertNotNull(proxy, "Expecting proxy not null");
        Assertions.assertTrue(proxy.getProxyName() == "colors", "Expecting proxy.getProxyName() == 'color'");
        Assertions.assertTrue(data.length == 3, "Expecting data.length == 3");
        Assertions.assertTrue(data[0] == "red", "Expecting data[0] == 'red'");
        Assertions.assertTrue(data[1] == "green", "Expecting data[1] == 'green'");
        Assertions.assertTrue(data[2] == "blue", "Expecting data[2] == 'blue'");
    }

}
