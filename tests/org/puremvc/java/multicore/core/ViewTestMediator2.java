//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.interfaces.IMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

/**
 * A Mediator class used by ViewTest.
 *
 * @see org.puremvc.java.multicore.core.ViewTest ViewTest
 */
public class ViewTestMediator2 extends Mediator implements IMediator {

    /**
     * The Mediator name
     */
    public static final String NAME = "ViewTestMediator2";

    /**
     * Constructor
     */
    public ViewTestMediator2(Object view) {
        super(NAME, view);
    }

    public String[] listNotificationInterests() {
        // be sure that the mediator has some Observers created
        // in order to test removeMediator
        return new String[] {ViewTest.NOTE1, ViewTest.NOTE2};
    }

    public void handleNotification(INotification notification) {
        getViewTest().lastNotification = notification.getName();
    }

    public ViewTest getViewTest() {
        return (ViewTest) viewComponent;
    }

}
