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
 * @see ViewTest ViewTest
 */
public class ViewTestMediator3 extends Mediator implements IMediator {

    /**
     * The Mediator name
     */
    public static final String NAME = "ViewTestMediator3";

    /**
     * Constructor
     */
    public ViewTestMediator3(Object view) {
        super(NAME, view);
    }

    @Override
    public String[] listNotificationInterests() {
        // be sure that the mediator has some Observers created
        // in order to test removeMediator
        return new String[] {ViewTest.NOTE3};
    }

    @Override
    public void handleNotification(INotification notification) {
        getViewTest().lastNotification = notification.getName();
    }

    public ViewTest getViewTest() {
        return (ViewTest) viewComponent;
    }

}
