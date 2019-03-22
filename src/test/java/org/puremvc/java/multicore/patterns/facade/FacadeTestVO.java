//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.facade;

/**
 * A utility class used by FacadeTest.
 *
 * @see FacadeTest FacadeTest
 * @see org.puremvc.java.multicore.patterns.facade.FacadeTestCommand FacadeTestCommand
 */
public class FacadeTestVO {

    public int input;
    public int result;

    /**
     * Constructor.
     *
     * @param input the number to be fed to the FacadeTestCommand
     */
    public FacadeTestVO(int input) {
        this.input = input;
    }
}
