//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.command;

/**
 * A utility class used by MacroCommandTest.
 *
 * @see org.puremvc.java.multicore.patterns.command.MacroCommandTest MacroCommandTest
 * @see org.puremvc.java.multicore.patterns.command.MacroCommandTestCommand MacroCommandTestCommand
 * @see org.puremvc.java.multicore.patterns.command.MacroCommandTestSub1Command MacroCommandTestSub1Command
 * @see org.puremvc.java.multicore.patterns.command.MacroCommandTestSub2Command MacroCommandTestSub2Command
 */
public class MacroCommandTestVO {

    public int input;
    public int result1;
    public int result2;

    /**
     * Constructor.
     *
     * @param input the number to be fed to the MacroCommandTestCommand
     */
    public MacroCommandTestVO(int input) {
        this.input = input;
    }
}
