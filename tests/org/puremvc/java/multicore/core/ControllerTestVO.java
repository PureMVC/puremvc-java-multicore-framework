//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

/**
 * A utility class used by ControllerTest.
 *
 * @see org.puremvc.java.multicore.core.ControllerTest ControllerTest
 * @see org.puremvc.java.multicore.core.ControllerTestCommand ControllerTestCommand
 */
public class ControllerTestVO {

    int input = 0;
    int result = 0;

    ControllerTestVO(int input) {
        this.input = input;
    }
}
