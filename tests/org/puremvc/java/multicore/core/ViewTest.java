//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.puremvc.java.multicore.interfaces.IMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.interfaces.IView;
import org.puremvc.java.multicore.patterns.mediator.Mediator;
import org.puremvc.java.multicore.patterns.observer.Notification;
import org.puremvc.java.multicore.patterns.observer.Observer;

/**
 * Test the PureMVC View class.
 */
public class ViewTest {

    public String lastNotification;
    public boolean onRegisterCalled = false;
    public boolean onRemoveCalled = false;
    public int counter = 0;

    public static final String NOTE1 = "Notification1";
    public static final String NOTE2 = "Notification2";
    public static final String NOTE3 = "Notification3";
    public static final String NOTE4 = "Notification4";
    public static final String NOTE5 = "Notification5";
    public static final String NOTE6 = "Notification6";


    /**
     * Tests the View Multiton Factory Method
     */
    @Test
    public void testGetInstance() {
        // Test Factory Method
        IView view = View.getInstance("ViewTestKey1", () -> new View("ViewTestKey1"));

        // test assertions
        Assertions.assertTrue(view != null, "Expecting instance not null");
        Assertions.assertNotNull((IView)view, "Expecting instance implements IView");
    }

    /**
     * Tests registration and notification of Observers.
     *
     * <P>
     * An Observer is created to callback the viewTestMethod of
     * this ViewTest instance. This Observer is registered with
     * the View to be notified of 'ViewTestEvent' events. Such
     * an event is created, and a value set on its payload. Then
     * the View is told to notify interested observers of this
     * Event.</P>
     *
     * <P>
     * The View calls the Observer's notifyObserver method
     * which calls the viewTestMethod on this instance
     * of the ViewTest class. The viewTestMethod method will set
     * an instance variable to the value passed in on the Event
     * payload. We evaluate the instance variable to be sure
     * it is the same as that passed out as the payload of the
     * original 'ViewTestEvent'.</P>
     */
    @Test
    public void testRegisterAndNotifyObserver() {
        // Get the Multiton View instance
        IView view = View.getInstance("ViewTestKey2", () -> new View("ViewTestKey2"));

        // Create observer, passing in notification method and context
        Observer observer = new Observer(this::viewTestMethod, this);

        // Register Observer's interest in a particulat Notification with the View
        view.registerObserver(ViewTestNote.NAME, observer);

        // Create a ViewTestNote, setting
        // a body value, and tell the View to notify
        // Observers. Since the Observer is this class
        // and the notification method is viewTestMethod,
        // successful notification will result in our local
        // viewTestVar being set to the value we pass in
        // on the note body.
        INotification note = ViewTestNote.create(10);
        view.notifyObservers(note);

        // test assertions
        Assertions.assertTrue(viewTestVar == 10, "Expecting viewTestVar = 10");
    }

    /**
     * A test variable that proves the viewTestMethod was
     * invoked by the View.
     */
    private int viewTestVar;

    /**
     * A utility method to test the notification of Observers by the view
     */
    private void viewTestMethod(INotification note) {
        // set the local viewTestVar to the number on the event payload
        viewTestVar = (int) note.getBody();
    }

    /**
     * Tests registering and retrieving a mediator with
     * the View.
     */
    @Test
    public void testRegisterAndRetrieveMediator() {
        // Get the Multiton View instance
        IView view = View.getInstance("ViewTestKey3", () -> new View("ViewTestKey3"));

        // Create and register the test mediator
        ViewTestMediator viewTestMediator = new ViewTestMediator(this);
        view.registerMediator(viewTestMediator);

        // Retrieve the component
        IMediator mediator = view.retrieveMediator(ViewTestMediator.NAME);

        // test assertions
        Assertions.assertNotNull((ViewTestMediator)mediator, "Expecting mediator is ViewTestMediator");
    }

    /**
     * Tests the hasMediator Method
     */
    @Test
    public void testHasMediator() {
        // Get the Multiton View instance
        IView view = View.getInstance("ViewTestKey4", () -> new View("ViewTestKey4"));

        // Create and register the test mediator
        Mediator mediator = new Mediator("hasMediatorTest", this);
        view.registerMediator(mediator);

        // assert that the view.hasMediator method returns true
        // for that mediator name
        Assertions.assertTrue(view.hasMediator("hasMediatorTest") == true, "Expecting view.hasMediator('hasMediatorTest') == true");

        view.removeMediator("hasMediatorTest");

        // assert that the view.hasMediator method returns false
        // for that mediator name
        Assertions.assertTrue(view.hasMediator("hasMediatorTest") == false, "Expecting view.hasMediator('hasMediatorTest') == false");
    }

    /**
     * Tests registering and removing a mediator
     */
    @Test
    public void testRegisterAndRemoveMediator() {
        // Get the Multiton View instance
        IView view = View.getInstance("ViewTestKey5", () -> new View("ViewTestKey5"));

        // Create and register the test mediator
        IMediator mediator = new Mediator("testing", this);
        view.registerMediator(mediator);

        // Remove the component
        IMediator removedMediator = view.removeMediator("testing");

        // assert that we have removed the appropriate mediator
        Assertions.assertTrue(removedMediator.getMediatorName() == "testing", "Expecting removedMediator.getMediatorName() == 'testing'");

        // assert that the mediator is no longer retrievable
        Assertions.assertTrue(view.retrieveMediator("testing") == null, "Expecting view.retrieveMediator('testing') == null");
    }

    /**
     * Tests that the View callse the onRegister and onRemove methods
     */
    @Test
    public void testOnRegisterAndOnRemove() {
        // Get the Multiton View instance
        IView view = View.getInstance("ViewTestKey6", () -> new View("ViewTestKey6"));

        // Create and register the test mediator
        IMediator mediator = new ViewTestMediator4(this);
        view.registerMediator(mediator);

        // assert that onRegsiter was called, and the mediator responded by setting our boolean
        Assertions.assertTrue(onRegisterCalled, "Expecting onRegisterCalled == true");

        // Remove the component
        view.removeMediator(ViewTestMediator4.NAME);

        // assert that the mediator is no longer retrievable
        Assertions.assertTrue(onRemoveCalled, "Expecting onRemoveCalled == true");
    }

    /**
     * Tests successive regster and remove of same mediator.
     */
    @Test
    public void testSuccessiveRegisterAndRemoveMediator() {
        // Get the Multiton View instance
        IView view = View.getInstance("ViewTestKey7", () -> new View("ViewTestKey7"));

        // Create and register the test mediator,
        // but not so we have a reference to it
        view.registerMediator(new ViewTestMediator(this));

        // test that we can retrieve it
        Assertions.assertNotNull((ViewTestMediator)view.retrieveMediator(ViewTestMediator.NAME), "Expecting view.retrieveMediator(ViewTestMediator.NAME) is ViewTestMediator");

        // Remove the Mediator
        view.removeMediator(ViewTestMediator.NAME);

        // test that retrieving it now returns null
        Assertions.assertTrue(view.retrieveMediator(ViewTestMediator.NAME) == null, "Expecting view.retrieveMediator(ViewTestMediator.NAME) == null");

        // test that removing the mediator again once its gone doesn't cause crash
        Assertions.assertNull(view.removeMediator(ViewTestMediator.NAME), "Expecting view.removeMediator(ViewTestMediator.NAME) doesn't crash");

        // Create and register another instance of the test mediator,
        view.registerMediator(new ViewTestMediator(this));

        Assertions.assertNotNull((ViewTestMediator)view.retrieveMediator(ViewTestMediator.NAME), "Expecting view.retrieveMediator(ViewTestMediator.NAME) is ViewTestMediator");

        // Remove the Mediator
        view.removeMediator(ViewTestMediator.NAME);

        // test that retrieving it now returns null
        Assertions.assertNull(view.removeMediator(ViewTestMediator.NAME), "Expecting view.removeMediator(ViewTestMediator.NAME) doesn't crash");
    }

    /**
     * Tests registering a Mediator for 2 different notifications, removing the
     * Mediator from the View, and seeing that neither notification causes the
     * Mediator to be notified. Added for the fix deployed in version 1.7
     */
    @Test
    public void testRemoveMediatorAndSubsequentNotify() {
        // Get the Multiton View instance
        IView view = View.getInstance("ViewTestKey8", () -> new View("ViewTestKey8"));

        // Create and register the test mediator to be removed.
        view.registerMediator(new ViewTestMediator2(this));

        // test that notifications work
        view.notifyObservers(new Notification(NOTE1));
        Assertions.assertTrue(lastNotification == NOTE1, "Expecting lastNotification == NOTE1");

        view.notifyObservers(new Notification(NOTE2));
        Assertions.assertTrue(lastNotification == NOTE2, "Expecting lastNotification == NOTE2");

        // Remove the Mediator
        view.removeMediator(ViewTestMediator2.NAME);

        // test that retrieving it now returns null
        Assertions.assertNull(view.retrieveMediator(ViewTestMediator2.NAME), "Expecting view.retrieveMediator((ViewTestMediator2.NAME) == null)");

        // test that notifications no longer work
        // (ViewTestMediator2 is the one that sets lastNotification
        // on this component, and ViewTestMediator)
        lastNotification = null;

        view.notifyObservers(new Notification(NOTE1));
        Assertions.assertTrue(lastNotification != NOTE1, "Expecting lastNotification != NOTE1");

        view.notifyObservers(new Notification(NOTE2));
        Assertions.assertTrue(lastNotification != NOTE2, "Expecting lastNotification != NOTE2");
    }

    /**
     * Tests registering one of two registered Mediators and seeing
     * that the remaining one still responds.
     */
    @Test
    public void testRemoveOneOfTwoMediatorsAndSubsequentNotify() {
        // Get the Multiton View instance
        IView view = View.getInstance("ViewTestKey9", () -> new View("ViewTestKey9"));

        // Create and register that responds to notifications 1 and 2
        view.registerMediator(new ViewTestMediator2(this));

        // Create and register that responds to notification 3
        view.registerMediator(new ViewTestMediator3(this));

        // test that all notifications work
        view.notifyObservers(new Notification(NOTE1));
        Assertions.assertTrue(lastNotification == NOTE1, "Expecting lastNotification == NOTE1");

        view.notifyObservers(new Notification(NOTE2));
        Assertions.assertTrue(lastNotification == NOTE2, "Expecting lastNotification == NOTE2");

        view.notifyObservers(new Notification(NOTE3));
        Assertions.assertTrue(lastNotification == NOTE3, "Expecting lastNotification == NOTE3");

        // Remove the Mediator that responds to 1 and 2
        view.removeMediator(ViewTestMediator2.NAME);

        // test that retrieving it now returns null
        Assertions.assertNull(view.retrieveMediator(ViewTestMediator2.NAME), "Expecting view.retrieveMediator(ViewTestMediator2.NAME) == null");

        // test that notifications no longer work
        // for notifications 1 and 2, but still work for 3
        lastNotification = null;

        view.notifyObservers(new Notification(NOTE1));
        Assertions.assertTrue(lastNotification != NOTE1, "Expecting lastNotification != NOTE1");

        view.notifyObservers(new Notification(NOTE2));
        Assertions.assertTrue(lastNotification != NOTE2, "Expecting lastNotification != NOTE2");

        view.notifyObservers(new Notification(NOTE3));
        Assertions.assertTrue(lastNotification == NOTE3, "Expecting lastNotification != NOTE3");
    }

    /**
     * Tests registering the same mediator twice.
     * A subsequent notification should only illicit
     * one response. Also, since reregistration
     * was causing 2 observers to be created, ensure
     * that after removal of the mediator there will
     * be no further response.
     */
    @Test
    public void testMediatorReregistration() {
        // Get the Singleton View instance
        IView view = View.getInstance("ViewTestKey10", () -> new View("ViewTestKey10"));

        // Create and register that responds to notification 5
        view.registerMediator(new ViewTestMediator5(this));

        // try to register another instance of that mediator (uses the same NAME constant).
        view.registerMediator(new ViewTestMediator5(this));

        // test that the counter is only incremented once (mediator 5's response)
        counter = 0;
        view.notifyObservers(new Notification(NOTE5));
        Assertions.assertEquals(1, counter, "Expecting counter == 1");

        // Remove the Mediator
        view.removeMediator(ViewTestMediator5.NAME);

        // test that retrieving it now returns null
        Assertions.assertNull(view.retrieveMediator(ViewTestMediator5.NAME), "Expecting view.retrieveMediator(ViewTestMediato5.NAME) == null");

        // test that the counter is no longer incremented
        counter = 0;
        view.notifyObservers(new Notification(NOTE5));
        Assertions.assertEquals(0, counter, "Expecting counter == 1");
    }

    /**
     * Tests the ability for the observer list to
     * be modified during the process of notification,
     * and all observers be properly notified. This
     * happens most often when multiple Mediators
     * respond to the same notification by removing
     * themselves.
     */
    @Test
    public void testModifyObserverListDuringNotification() {
        // Get the Singleton View instance
        IView view = View.getInstance("ViewTestKey11", () -> new View("ViewTestKey11"));

        // Create and register several mediator instances that respond to notification 6
        // by removing themselves, which will cause the observer list for that notification
        // to change. versions prior to MultiCore Version 2.0.5 will see every other mediator
        // fails to be notified.
        view.registerMediator(new ViewTestMediator6(ViewTestMediator6.NAME + "/1", this));
        view.registerMediator(new ViewTestMediator6(ViewTestMediator6.NAME + "/2", this));
        view.registerMediator(new ViewTestMediator6(ViewTestMediator6.NAME + "/3", this));
        view.registerMediator(new ViewTestMediator6(ViewTestMediator6.NAME + "/4", this));
        view.registerMediator(new ViewTestMediator6(ViewTestMediator6.NAME + "/5", this));
        view.registerMediator(new ViewTestMediator6(ViewTestMediator6.NAME + "/6", this));
        view.registerMediator(new ViewTestMediator6(ViewTestMediator6.NAME + "/7", this));
        view.registerMediator(new ViewTestMediator6(ViewTestMediator6.NAME + "/8", this));

        // clear the counter
        counter = 0;

        // send the notification. each of the above mediators will respond by removing
        // themselves and incrementing the counter by 1. This should leave us with a
        // count of 8, since 8 mediators will respond.
        view.notifyObservers(new Notification(NOTE6));
        // verify the count is correct
        Assertions.assertEquals(8, counter, "Expecting counter == 8");

        // clear the counter
        counter = 0;
        view.notifyObservers(new Notification(NOTE6));
        // verify the count is 0
        Assertions.assertEquals(0, counter, "Expecting counter == 0");
    }
}
