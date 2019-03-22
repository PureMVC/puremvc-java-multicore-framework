//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

/**
 * A Mediator class used by ViewTest.
 *
 * @see ViewTest ViewTest
 */
public class ViewTestMediator6 extends Mediator {

    /**
     * The Mediator base name
     */
    public static final String NAME = "ViewTestMediator6";

    /**
     * Constructor
     */
    public ViewTestMediator6(String name, Object view) {
        super(name, view);
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{ViewTest.NOTE6};
    }

    @Override
    public void handleNotification(INotification notification) {
        getFacade().removeMediator(getMediatorName());
    }

    @Override
    public void onRemove() {
        viewTest().counter++;
    }

    public ViewTest viewTest() {
        return (ViewTest)viewComponent;
    }
}
