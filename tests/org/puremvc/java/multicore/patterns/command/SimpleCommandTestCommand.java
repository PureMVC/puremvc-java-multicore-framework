//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.command;

import org.puremvc.java.multicore.interfaces.INotification;

/**
 * A SimpleCommand subclass used by SimpleCommandTest.
 *
 * @see org.puremvc.java.multicore.patterns.command.SimpleCommandTest SimpleCommandTest
 * @see org.puremvc.java.multicore.patterns.command.SimpleCommandTestVO SimpleCommandTestVO
 */
public class SimpleCommandTestCommand extends SimpleCommand {

    /**
     * Fabricate a result by multiplying the input by 2
     *
     * @param notification the <code>INotification</code> carrying the <code>SimpleCommandTestVO</code>
     */
    public void execute(INotification notification) {
        SimpleCommandTestVO vo = (SimpleCommandTestVO) notification.getBody();

        // Fabricate a result
        vo.result = 2 * vo.input;
    }
}
