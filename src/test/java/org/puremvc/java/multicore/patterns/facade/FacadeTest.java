//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.puremvc.java.multicore.interfaces.IFacade;
import org.puremvc.java.multicore.interfaces.IMediator;
import org.puremvc.java.multicore.interfaces.IProxy;
import org.puremvc.java.multicore.patterns.mediator.Mediator;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Test the PureMVC Facade class.
 *
 * @see org.puremvc.java.multicore.patterns.facade.FacadeTestVO FacadeTestVO
 * @see org.puremvc.java.multicore.patterns.facade.FacadeTestCommand FacadeTestCommand
 */
public class FacadeTest {

    /**
     * Tests the Facade Multiton Factory Method
     */
    @Test
    public void testGetInstance() {
        // Test Factory Method
        IFacade facade = Facade.getInstance("FacadeTestKey1", key -> new Facade(key));

        // test assertions
        Assertions.assertNotNull(facade, "Expecting instance not null");
        Assertions.assertNotNull((IFacade)facade, "Expecting instance implements IFacade");
    }

    /**
     * Tests Command registration and execution via the Facade.
     *
     * <P>This test gets a Multiton Facade instance
     * and registers the FacadeTestCommand class
     * to handle 'FacadeTest' Notifcations.<P>
     *
     * <P>It then sends a notification using the Facade.
     * Success is determined by evaluating
     * a property on an object placed in the body of
     * the Notification, which will be modified by the Command.</P>
     *
     */
    @Test
    public void testRegisterCommandAndSendNotification() {
        // Create the Facade, register the FacadeTestCommand to
        // handle 'FacadeTest' notifications
        IFacade facade = Facade.getInstance("FacadeTestKey2", key -> new Facade(key));
        facade.registerCommand("FacadeTestNote", () -> new FacadeTestCommand());

        // Send notification. The Command associated with the event
        // (FacadeTestCommand) will be invoked, and will multiply
        // the vo.input value by 2 and set the result on vo.result
        FacadeTestVO vo = new FacadeTestVO(32);
        facade.sendNotification("FacadeTestNote", vo);

        // test assertions
        Assertions.assertTrue(vo.result == 64, "Expecting vo.result == 64");
    }

    /**
     * Tests Command removal via the Facade.
     *
     * <P>This test gets a Multiton Facade instance
     * and registers the FacadeTestCommand class
     * to handle 'FacadeTest' Notifcations. Then it removes the command.<P>
     *
     * <P>It then sends a Notification using the Facade.
     * Success is determined by evaluating
     * a property on an object placed in the body of
     * the Notification, which will NOT be modified by the Command.</P>
     *
     */
    @Test
    public void testRegisterAndRemoveCommandAndSendNotification() {
        // Create the Facade, register the FacadeTestCommand to
        // handle 'FacadeTest' events
        IFacade facade = Facade.getInstance("FacadeTestKey3", key -> new Facade(key));
        facade.registerCommand("FacadeTestNote", () -> new FacadeTestCommand());
        facade.removeCommand("FacadeTestNote");

        // Send notification. The Command associated with the event
        // (FacadeTestCommand) will NOT be invoked, and will NOT multiply
        // the vo.input value by 2
        FacadeTestVO vo = new FacadeTestVO(32);
        facade.sendNotification("FacadeTestNote", vo);

        // test assertions
        Assertions.assertTrue(vo.result != 64, "Expecting vo.result != 64");
    }

    /**
     * Tests the regsitering and retrieving Model proxies via the Facade.
     *
     * <P>Tests <code>registerProxy</code> and <code>retrieveProxy</code> in the same test.
     * These methods cannot currently be tested separately
     * in any meaningful way other than to show that the
     * methods do not throw exception when called. </P>
     */
    @Test
    public void testRegisterAndRetrieveProxy() {
        // register a proxy and retrieve it.
        IFacade facade = Facade.getInstance("FacadeTestKey4", key -> new Facade(key));
        facade.registerProxy(new Proxy("colors", new String[]{"red", "green", "blue"}));
        IProxy proxy = facade.retrieveProxy("colors");

        // test assertions
        Assertions.assertNotNull((IProxy)proxy, "Expecting prixy is IProxy");

        // retrieve data from proxy
        String[] data = (String[])proxy.getData();

        // test assertions
        Assertions.assertNotNull(data, "Expecting data not null");
        Assertions.assertNotNull((String[])data, "Expecting data is array");
        Assertions.assertTrue(data.length == 3, "Expecting data.length == 3");
        Assertions.assertTrue(data[0] == "red", "Expecting data[0] == 'red'");
        Assertions.assertTrue(data[1] == "green", "Expecting data[1] == 'green'");
        Assertions.assertTrue(data[2] == "blue", "Expecting data[2] == 'blue'");
    }

    /**
     * Tests the removing Proxies via the Facade.
     */
    @Test
    public void testRegisterAndRemoveProxy() {
        // register a proxy, remove it, then try to retrieve it
        IFacade facade = Facade.getInstance("FacadeTestKey5", key -> new Facade(key));
        IProxy proxy = new Proxy("sizes", new String[]{"7", "13", "21"});
        facade.registerProxy(proxy);

        // remove the proxy
        IProxy removedProxy = facade.removeProxy("sizes");

        // assert that we removed the appropriate proxy
        Assertions.assertTrue(removedProxy.getProxyName() == "sizes", "Expecting removedProxy.getProxyName() == 'sizes'");

        // make sure we can no longer retrieve the proxy from the model
        proxy = facade.retrieveProxy("sizes");

        // test assertions
        Assertions.assertNull(proxy, "Expecting proxy is null");
    }

    /**
     * Tests registering, retrieving and removing Mediators via the Facade.
     */
    @Test
    public void testRegisterRetrieveAndRemoveMediator() {
        // register a mediator, remove it, then try to retrieve it
        IFacade facade = Facade.getInstance("FacadeTestKey6", key -> new Facade(key));
        facade.registerMediator(new Mediator(Mediator.NAME, new Object()));

        // retrieve the mediator
        Assertions.assertNotNull(facade.retrieveMediator(Mediator.NAME), "Expecting mediator is not null");

        // remove the mediator
        IMediator removedMediator = facade.removeMediator(Mediator.NAME);

        // assert that we have removed the appropriate mediator
        Assertions.assertTrue(removedMediator.getMediatorName() == Mediator.NAME, "Expecting removedMediator.getMediatorName() == Mediator.NAME");

        // assert that the mediator is no longer retrievable
        Assertions.assertNull(facade.retrieveMediator(Mediator.NAME), "Expecting facade.retrieveMediator(Mediator.NAME) == null");
    }

    /**
     * Tests the hasProxy Method
     */
    @Test
    public void testHasProxy() {
        // register a Proxy
        IFacade facade = Facade.getInstance("FacadeTestKey7", key -> new Facade(key));
        facade.registerProxy(new Proxy("hasProxyTest", new Integer[]{1, 2, 3,}));

        // assert that the model.hasProxy method returns true
        // for that proxy name
        Assertions.assertTrue(facade.hasProxy("hasProxyTest"), "Expecting facade.hasProxy('hasProxyTest') == true");
    }

    /**
     * Tests the hasMediator Method
     */
    @Test
    public void testHasMediator() {
        // register a Mediator
        IFacade facade = Facade.getInstance("FacadeTestKey8", key -> new Facade(key));
        facade.registerMediator(new Mediator("facadeHasMediatorTest", new Object()));

        // assert that the facade.hasMediator method returns true
        // for that mediator name
        Assertions.assertTrue(facade.hasMediator("facadeHasMediatorTest"), "Expecting facade.hasMediator('facadeHasMediatorTest') == true");

        facade.removeMediator("facadeHasMediatorTest");

        // assert that the facade.hasMediator method returns false
        // for that mediator name
        Assertions.assertFalse(facade.hasMediator("facadeHasMediatorTest"), "Expecting facade.hasMediator('facadeHasMediatorTest') == false");
    }

    /**
     * Test hasCommand method.
     */
    @Test
    public void testHasCommand() {
        // register the ControllerTestCommand to handle 'hasCommandTest' notes
        IFacade facade = Facade.getInstance("FacadeTestKey10", key -> new Facade(key));
        facade.registerCommand("facadeHasCommandTest", () -> new FacadeTestCommand());

        // test that hasCommand returns true for hasCommandTest notifications
        Assertions.assertTrue(facade.hasCommand("facadeHasCommandTest"), "Expecting facade.hasCommand('facadeHasCommandTest') == true");

        // Remove the Command from the Controller
        facade.removeCommand("facadeHasCommandTest");

        // test that hasCommand returns false for hasCommandTest notifications
        Assertions.assertFalse(facade.hasCommand("facadeHasCommandTest"), "Expecting facade.hasCommand('facadeHasCommandTest')");
    }

    /**
     * Tests the hasCore and removeCore methods
     */
    @Test
    public void testHasCoreAndRemoveCore() {
        // assert that the Facade.hasCore method returns false first
        Assertions.assertFalse(Facade.hasCore("FacadeTestKey11"), "Expecting facade.hasCore('FacadeTestKey11') == false");

        // register a Core
        IFacade facade = Facade.getInstance("FacadeTestKey11", key -> new Facade(key));

        // assert that the Facade.hasCore method returns true now that a Core is registered
        Assertions.assertTrue(Facade.hasCore("FacadeTestKey11"), "Expecting facade.hasCore('FacadeTestKey11') == true");

        // remove the Core
        Facade.removeCore("FacadeTestKey11");

        // assert that the Facade.hasCore method returns false now that the core has been removed.
        Assertions.assertFalse(Facade.hasCore("FacadeTestKey11"), "Expecting Facade.hasCore('FacadeTestKey11') == false");
    }

}
