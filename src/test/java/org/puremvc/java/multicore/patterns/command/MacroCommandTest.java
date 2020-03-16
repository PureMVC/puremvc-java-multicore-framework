//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.observer.Notification;

/**
 * Test the PureMVC SimpleCommand class.
 *
 * @see org.puremvc.java.multicore.patterns.command.MacroCommandTestVO MacroCommandTestVO
 * @see org.puremvc.java.multicore.patterns.command.MacroCommandTestCommand MacroCommandTestCommand
 */
public class MacroCommandTest {

    /**
     * Tests operation of a <code>MacroCommand</code>.
     *
     * <P>This test creates a new <code>Notification</code>, adding a
     * <code>MacroCommandTestVO</code> as the body.
     * It then creates a <code>MacroCommandTestCommand</code> and invokes
     * its <code>execute</code> method, passing in the
     * <code>Notification</code>.<P>
     *
     * <P>The <code>MacroCommandTestCommand</code> has defined an
     * <code>initializeMacroCommand</code> method, which is
     * called automatically by its constructor. In this method
     * the <code>MacroCommandTestCommand</code> adds 2 SubCommands
     * to itself, <code>MacroCommandTestSub1Command</code> and
     * <code>MacroCommandTestSub2Command</code>.
     *
     * <P>The <code>MacroCommandTestVO</code> has 2 result properties,
     * one is set by <code>MacroCommandTestSub1Command</code> by
     * multiplying the input property by 2, and the other is set
     * by <code>MacroCommandTestSub2Command</code> by multiplying
     * the input property by itself.
     *
     * <P>Success is determined by evaluating the 2 result properties
     * on the <code>MacroCommandTestVO</code> that was passed to
     * the <code>MacroCommandTestCommand</code> on the Notification
     * body.</P>
     */
    @Test
    public void testMacroCommandExecute() {

        // Create the VO
        MacroCommandTestVO vo = new MacroCommandTestVO(5);

        // Create the Notification (note)
        INotification note = new Notification("MacroCommandTest", vo, null);

        // Create the SimpleCommand
        MacroCommandTestCommand command = new MacroCommandTestCommand();
        command.initializeNotifier("test");

        // Execute the SimpleCommand
        command.execute(note);

        // test assertions
        Assertions.assertTrue(vo.result1 == 10, "Expecting vo.result1 == 10 " + vo.result1);
        Assertions.assertTrue(vo.result2 == 25, "Expecing vo.result2 == 25");
    }
}
