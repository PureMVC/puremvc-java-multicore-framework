//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.mediator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the PureMVC Mediator class.
 *
 * @see org.puremvc.java.multicore.interfaces.IMediator IMediator
 * @see org.puremvc.java.multicore.patterns.mediator.Mediator Mediator
 */
public class MediatorTest {

    /**
     * Tests getting the name using Mediator class accessor method.
     */
    @Test
    public void testNameAccessor() {
        // Create a new Mediator and use accessors to set the mediator name
        Mediator mediator = new Mediator();

        // test assertions
        Assertions.assertTrue(mediator.getMediatorName() == Mediator.NAME, "Expecting mediator.getMediatorName() == Mediator.NAME");
    }

    /**
     * Tests getting the name using Mediator class accessor method.
     */
    @Test
    public void testViewAccessor() {
        // Create a view object
        Object view = new Object();

        // Create a new Proxy and use accessors to set the proxy name
        Mediator mediator = new Mediator(Mediator.NAME, view);

        // test assertions
        Assertions.assertNotNull(mediator.getViewComponent(), "Expecting mediator.getViewComponent() not null");
    }

}
