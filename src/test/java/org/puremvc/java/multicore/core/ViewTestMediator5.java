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
public class ViewTestMediator5 extends Mediator implements IMediator {

    /**
     * The Mediator name
     */
    public static final String NAME = "ViewTestMediator5";

    /**
     * Constructor
     */
    public ViewTestMediator5(Object view) {
        super(NAME, view);
    }

    public String[] listNotificationInterests() {
        return new String[] {ViewTest.NOTE5};
    }

    public void handleNotification(INotification notification) {
        getViewTest().counter++;
    }

    public ViewTest getViewTest() {
        return (ViewTest) viewComponent;
    }

}
