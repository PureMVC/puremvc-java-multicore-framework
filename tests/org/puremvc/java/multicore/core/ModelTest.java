//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.puremvc.java.multicore.interfaces.IModel;
import org.puremvc.java.multicore.interfaces.IProxy;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Test the PureMVC Model class.
 */
public class ModelTest {

    /**
     * Tests the Model Multiton Factory Method
     */
    public void testGetInstance() {
        // Test Factory Method
        IModel model = Model.getInstance("ModelTestKey1", () -> new Model("ModelTestKey1"));

        // test assertions
        Assertions.assertNotNull(model, "Expecting instance not null");
        Assertions.assertNotNull((IModel) model, "Expecting instance implements IModel");
    }

    /**
     * Tests the proxy registration and retrieval methods.
     *
     * <P>
     * Tests <code>registerProxy</code> and <code>retrieveProxy</code> in the same test.
     * These methods cannot currently be tested separately
     * in any meaningful way other than to show that the
     * methods do not throw exception when called. </P>
     */
    public void testRegisterAndRetrieveProxy() {
        // register a proxy and retrieve it.
        IModel model = Model.getInstance("ModelTestKey2", () -> new Model("ModelTestKey2"));
        model.registerProxy(new Proxy("colors", new String[]{"red", "green", "blue"}));
        IProxy proxy = model.retrieveProxy("colors");
        String[] data = (String[]) proxy.getData();

        // test assertions
        Assertions.assertNotNull(data, "Expecting data not null");
        Assertions.assertNotNull((String[])data, "Expecting data type is Array");
        Assertions.assertTrue(data.length == 3, "Expecting data.length == 3");
        Assertions.assertTrue(data[0] == "red", "Expecting data[0] == 'red'");
        Assertions.assertTrue(data[1] == "green", "Expecting data[1] == 'green'");
        Assertions.assertTrue(data[2] == "blue", "Expecting data[2] == 'blue'");
    }

    /**
     * Tests the proxy removal method.
     */
    @Test
    public void testRegisterAndRemoveProxy() {
        // register a proxy, remove it, then try to retrieve it
        IModel model = Model.getInstance("ModelTestKey4", () -> new Model("ModelTestKey4"));
        IProxy proxy = new Proxy("sizes", new String[]{"7", "13", "21"});
        model.registerProxy(proxy);

        // remove the proxy
        IProxy removedProxy = model.removeProxy("sizes");

        // assert that we removed the appropriate proxy
        Assertions.assertTrue(removedProxy.getProxyName() == "sizes", "Expecting removedProxy.getProxyName() == 'sizes'");

        // ensure that the proxy is no longer retrievable from the model
        proxy = model.retrieveProxy("sizes");

        // test assertions
        Assertions.assertNull(proxy, "Expecting proxy is null");
    }

    /**
     * Tests the hasProxy Method
     */
    @Test
    public void testHasProxy() {
        // register a proxy
        IModel model = Model.getInstance("ModelTestKey4", () -> new Model("ModelTestKey4"));
        IProxy proxy = new Proxy("aces", new String[]{"clubs", "spades", "hearts", "diamonds"});
        model.registerProxy(proxy);

        // assert that the model.hasProxy method returns true
        // for that proxy name
        Assertions.assertTrue(model.hasProxy("aces") == true, "Expecting model.hasProxy('aces') == true");

        // remove the proxy
        model.removeProxy("aces");

        // assert that the model.hasProxy method returns false
        // for that proxy name
        Assertions.assertTrue(model.hasProxy("aces") == false, "Expecting model.hasProxy('aces') == false");
    }

    /**
     * Tests that the Model calls the onRegister and onRemove methods
     */
    @Test
    public void testOnRegisterAndOnRemove() {
        // Get a Multiton View instance
        IModel model = Model.getInstance("ModelTestKey4", () -> new Model("ModelTestKey4"));

        // Create and register the test mediator
        IProxy proxy = new ModelTestProxy();
        model.registerProxy(proxy);

        // assert that onRegsiter was called, and the proxy responded by setting its data accordingly
        Assertions.assertTrue(proxy.getData() == ModelTestProxy.ON_REGISTER_CALLED, "Expecting proxy.getData() == ModelTestProxy.ON_REGISTER_CALLED");

        // Remove the component
        model.removeProxy(ModelTestProxy.NAME);

        // assert that onRemove was called, and the proxy responded by setting its data accordingly
        Assertions.assertTrue(proxy.getData() == ModelTestProxy.ON_REMOVE_CALLED, "Expecting proxy.getData() == ModelTestProxy.ON_REMOVE_CALLED");
    }

}
