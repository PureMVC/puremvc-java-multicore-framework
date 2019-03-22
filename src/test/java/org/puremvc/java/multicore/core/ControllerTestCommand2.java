//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class ControllerTestCommand2 extends SimpleCommand {

    /**
     * Fabricate a result by multiplying the input by 2 and adding to the existing result
     *
     * <P>This tests accumulation effect that would show if the command were executed more than once.</P>
     *
     * @param notification the note carrying the ControllerTestVO
     */
    public void execute(INotification notification) {
        ControllerTestVO vo = (ControllerTestVO)notification.getBody();

        // Fabricate a result
        vo.result = vo.result + (2 * vo.input);
    }
}
