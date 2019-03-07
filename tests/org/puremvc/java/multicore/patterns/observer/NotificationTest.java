//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.puremvc.java.multicore.interfaces.INotification;

/**
 * Test the PureMVC Notification class.
 *
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notification
 */
public class NotificationTest {

    /**
     * Tests setting and getting the name using Notification class accessor methods.
     */
    @Test
    public void testNameAccessors() {
        // Create a new Notification and use accessors to set the note name
        INotification note = new Notification("TestNote");

        // test assertions
        Assertions.assertTrue(note.getName() == "TestNote", "Expecting note.getName() == 'TestNote'");
    }

    /**
     * Tests setting and getting the body using Notification class accessor methods.
     */
    @Test
    public void testBodyAccessors() {
        // Create a new Notification and use accessors to set the body
        INotification note = new Notification(null);
        note.setBody(5);

        // test assertions
        Assertions.assertTrue((int)note.getBody() == 5, "Expecting note.getBody() == 5");
    }

    /**
     * Tests setting the name and body using the Notification class Constructor.
     */
    @Test
    public void testConstructor() {
        // Create a new Notification using the Constructor to set the note name and body
        INotification note = new Notification("TestNote", 5, "TestNoteType");

        // test assertions
        Assertions.assertTrue(note.getName() == "TestNote", "Expecting note.getName() == 'TestNote'");
        Assertions.assertTrue((int)note.getBody() == 5, "Expecting note.getBody() == 5");
        Assertions.assertTrue(note.getType() == "TestNoteType", "Expecting note.getType() == 'TestNoteType'");
    }

    /**
     * Tests the toString method of the notification
     */
    @Test
    public void testToString() {
        // Create a new Notification and use accessors to set the note name
        INotification note = new Notification("TestNote", "1,3,5", "TestType");
        String ts = "Notification Name: TestNote\nBody:1,3,5\nType:TestType";

        // test assertions
        Assertions.assertTrue(note.toString().equals(ts), "Expecting note.toString() == '" + ts + "'");
    }
}
