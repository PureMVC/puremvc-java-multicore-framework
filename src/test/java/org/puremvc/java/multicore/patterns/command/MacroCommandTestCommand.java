//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.command;

/**
 * A MacroCommand subclass used by MacroCommandTest.
 *
 * @see MacroCommandTest MacroCommandTest
 * @see org.puremvc.java.multicore.patterns.command.MacroCommandTestSub1Command MacroCommandTestSub1Command
 * @see org.puremvc.java.multicore.patterns.command.MacroCommandTestSub2Command MacroCommandTestSub2Command
 * @see MacroCommandTestVO MacroCommandTestVO
 */
public class MacroCommandTestCommand extends MacroCommand {

    /**
     * Initialize the MacroCommandTestCommand by adding
     * its 2 SubCommands.
     */
    protected void initializeMacroCommand() {
        addSubCommand(() -> new MacroCommandTestSub1Command());
        addSubCommand(() -> new MacroCommandTestSub2Command());
    }
}
