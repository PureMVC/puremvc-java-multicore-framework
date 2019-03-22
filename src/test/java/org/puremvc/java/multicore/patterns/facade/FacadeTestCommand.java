//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.facade;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

/**
 * A SimpleCommand subclass used by FacadeTest.
 *
 * @see FacadeTest FacadeTest
 * @see FacadeTestVO FacadeTestVO
 */
public class FacadeTestCommand extends SimpleCommand {

    /**
     * Fabricate a result by multiplying the input by 2
     *
     * @param notification the Notification carrying the FacadeTestVO
     */
    public void execute(INotification notification) {
        FacadeTestVO vo = (FacadeTestVO)notification.getBody();

        // Fabricate a result
        vo.result = 2 * vo.input;
    }
}
