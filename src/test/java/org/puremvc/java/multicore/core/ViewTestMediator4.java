//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.interfaces.IMediator;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

/**
 * A Mediator class used by ViewTest.
 *
 * @see ViewTest ViewTest
 */
public class ViewTestMediator4 extends Mediator implements IMediator {

    /**
     * The Mediator name
     */
    public static final String NAME = "ViewTestMediator4";

    /**
     * Constructor
     */
    public ViewTestMediator4(Object view) {
        super(NAME, view);
    }

    public ViewTest getViewTest() {
        return (ViewTest) viewComponent;
    }

    @Override
    public void onRegister() {
        getViewTest().onRegisterCalled = true;
    }

    @Override
    public void onRemove() {
        getViewTest().onRemoveCalled = true;
    }
}
