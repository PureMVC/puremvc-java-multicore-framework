//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.puremvc.java.multicore.interfaces.IController;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.interfaces.IView;
import org.puremvc.java.multicore.patterns.observer.Notification;

/**
 * Test the PureMVC Controller class.
 *
 * @see org.puremvc.java.multicore.core.ControllerTestVO ControllerTestVO
 * @see org.puremvc.java.multicore.core.ControllerTestCommand ControllerTestCommand
 */
public class ControllerTest {

    /**
     * Tests the Controller Multiton Factory Method
     */
    @Test
    public void testGetInstance() {
        // Test Factory Method
        IController controller = Controller.getInstance("ControllerTestKey1", () -> new Controller("ControllerTestKey1"));

        // test assertion
        Assertions.assertNotNull(controller, "Expecting instance not null");
        Assertions.assertNotNull((IController)controller, "Expecting instance implements IController");
    }

    /**
     * Tests Command registration and execution.
     *
     * <P>
     * This test gets a Multiton Controller instance
     * and registers the ControllerTestCommand class
     * to handle 'ControllerTest' Notifications.<P>
     *
     * <P>
     * It then constructs such a Notification and tells the
     * Controller to execute the associated Command.
     * Success is determined by evaluating a property
     * on an object passed to the Command, which will
     * be modified when the Command executes.</P>
     */
    @Test
    public void testRegisterAndExecuteCommand() {
        // Create the controller, register the ControllerTestCommand to handle 'ControllerTest' notes
        IController controller = Controller.getInstance("ControllerTestKey2", () -> new Controller("ControllerTestKey2"));
        controller.registerCommand("ControllerTest", () -> new ControllerTestCommand());

        // Create a 'ControllerTest' note
        ControllerTestVO vo = new ControllerTestVO(12);
        INotification note = new Notification("ControllerTest", vo);

        // Tell the controller to execute the Command associated with the note
        // the ControllerTestCommand invoked will multiply the vo.input value
        // by 2 and set the result on vo.result
        controller.executeCommand(note);

        // test assertions
        Assertions.assertTrue(vo.result == 24, "Expecting vo.result == 24");
    }

    /**
     * Tests Command registration and removal.
     *
     * <P>
     * Tests that once a Command is registered and verified
     * working, it can be removed from the Controller.</P>
     */
    @Test
    public void testRegisterAndRemoveCommand() {
        // Create the controller, register the ControllerTestCommand to handle 'ControllerTest' notes
        IController controller = Controller.getInstance("ControllerTestKey3", () -> new Controller("ControllerTestKey3"));
        controller.registerCommand("ControllerRemoveTest", () -> new ControllerTestCommand());

        // Create a 'ControllerTest' note
        ControllerTestVO vo = new ControllerTestVO(12);
        INotification note = new Notification("ControllerRemoveTest", vo);

        // Tell the controller to execute the Command associated with the note
        // the ControllerTestCommand invoked will multiply the vo.input value
        // by 2 and set the result on vo.result
        controller.executeCommand(note);

        // test assertions
        Assertions.assertTrue(vo.result == 24, "Expecting vo.result == 24");

        // Reset result
        vo.result = 0;

        // Remove the Command from the Controller
        controller.removeCommand("ControllerRemoveTest");

        // Tell the controller to execute the Command associated with the
        // note. This time, it should not be registered, and our vo result
        // will not change
        controller.executeCommand(note);

        // test assertions
        Assertions.assertTrue(vo.result == 0, "Expecting vo.result == 0");
    }

    /**
     * Test hasCommand method.
     */
    @Test
    public void testHasCommand() {
        // register the ControllerTestCommand to handle 'hasCommandTest' notes
        IController controller = Controller.getInstance("ControllerTestKey4", () -> new Controller("ControllerTestKey4"));
        controller.registerCommand("hasCommandTest", () -> new ControllerTestCommand());

        // test that hasCommand returns true for hasCommandTest notifications
        Assertions.assertTrue(controller.hasCommand("hasCommandTest"), "Expecting controller.hasCommand('hasCommandTest') == true");

        // Remove the Command from the Controller
        controller.removeCommand("hasCommandTest");

        // test that hasCommand returns false for hasCommandTest notifications
        Assertions.assertFalse(controller.hasCommand("hasCommandTest"), "Expecting controller.hasCommand('hasCommandTest') == false");
    }

    /**
     * Tests Removing and Reregistering a Command
     *
     * <P>
     * Tests that when a Command is re-registered that it isn't fired twice.
     * This involves, minimally, registration with the controller but
     * notification via the View, rather than direct execution of
     * the Controller's executeCommand method as is done above in
     * testRegisterAndRemove. </P>
     */
    @Test
    public void testReRegisterAndExecuteCommand() {
        // Fetch the controller, register the ControllerTestCommand2 to handle 'ControllerTest2' notes
        IController controller = Controller.getInstance("ControllerTestKey5", () -> new Controller("ControllerTestKey5"));
        controller.registerCommand("ControllerTest2", () -> new ControllerTestCommand2());

        // Remove the Command from the Controller
        controller.removeCommand("ControllerTest2");

        // Re-register the Command with the Controller
        controller.registerCommand("ControllerTest2", () -> new ControllerTestCommand2());

        // Create a 'ControllerTest2' note
        ControllerTestVO vo = new ControllerTestVO(12);
        INotification note = new Notification("ControllerTest2", vo);

        // retrieve a reference to the View from the same core.
        IView view = View.getInstance("ControllerTestKey5", () -> new View("ControllerTestKey5"));

        // send the Notification
        view.notifyObservers(note);

        // test assertions
        // if the command is executed once the value will be 24
        Assertions.assertTrue(vo.result == 24, "Expecting vo.result == 24");

        // Prove that accumulation works in the VO by sending the notification again
        view.notifyObservers(note);

        // if the command is executed twice the value will be 48
        Assertions.assertTrue(vo.result == 48, "Expecting vo.result == 48");
    }

}
