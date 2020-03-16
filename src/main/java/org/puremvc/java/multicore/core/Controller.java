//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.interfaces.ICommand;
import org.puremvc.java.multicore.interfaces.IController;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.interfaces.IView;
import org.puremvc.java.multicore.patterns.observer.Observer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <P>A Multiton <code>IController</code> implementation.</P>
 *
 * <P>In PureMVC, the <code>Controller</code> class follows the
 * 'Command and Controller' strategy, and assumes these
 * responsibilities:</P>
 *
 * <UL>
 * <LI> Remembering which <code>ICommand</code>
 * are intended to handle which <code>INotifications</code>.</LI>
 * <LI> Registering itself as an <code>IObserver</code> with
 * the <code>View</code> for each <code>INotification</code>
 * that it has an <code>ICommand</code> mapping for.</LI>
 * <LI> Creating a new instance of the proper <code>ICommand</code>
 * to handle a given <code>INotification</code> when notified by the <code>View</code>.</LI>
 * <LI> Calling the <code>ICommand</code>'s <code>execute</code>
 * method, passing in the <code>INotification</code>.</LI>
 * </UL>
 *
 * <P>Your application must register <code>ICommands</code> with the
 * Controller.</P>
 *
 * <P>The simplest way is to subclass <code>Facade</code>,
 * and use its <code>initializeController</code> method to add your
 * registrations.</P>
 *
 * @see View View
 * @see org.puremvc.java.multicore.patterns.observer.Observer Observer
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notification
 * @see org.puremvc.java.multicore.patterns.command.SimpleCommand SimpleCommand
 * @see org.puremvc.java.multicore.patterns.command.MacroCommand MacroCommand
 */

public class Controller implements IController {

    // Local reference to View
    protected IView view;

    // Mapping of Notification names to Command Supplier references
    protected ConcurrentMap<String, Supplier<ICommand>> commandMap;

    // The Multiton Key for this Core
    protected String multitonKey;

    // The Multiton Controller instanceMap.
    protected static Map<String, IController> instanceMap = new HashMap<>();

    // Message Constants
    protected final String MULTITON_MSG = "Controller instance for this Multiton key already constructed!";

    /**
     * <P>Constructor.</P>
     *
     * <P>This <code>IController</code> implementation is a Multiton,
     * so you should not call the constructor
     * directly, but instead call the static Factory method,
     * passing the unique key and a supplier for this instance
     * {@code Controller.getInstance(multitonKey, () -> new Controller(multitonKey))}</P>
     *
     * @param key multitonKey
     * @throws Error Error if instance for this Multiton key has already been constructed
     *
     */
    public Controller(String key) {
        if(instanceMap.get(key) != null) throw new Error(MULTITON_MSG);
        multitonKey = key;
        instanceMap.put(key, this);
        commandMap = new ConcurrentHashMap<>();
        initializeController();
    }

    /**
     * <P>Initialize the Multiton <code>Controller</code> instance.</P>
     *
     * <P>Called automatically by the constructor.</P>
     *
     * <P>Note that if you are using a subclass of <code>View</code>
     * in your application, you should <i>also</i> subclass <code>Controller</code>
     * and override the <code>initializeController</code> method in the
     * following way:</P>
     *
     * <pre>
     * {@code // ensure that the Controller is talking to my IView implementation
     * void public initializeController(  )
     * {
     *     view = MyView.getInstance(multitonKey, () -> new MyView(multitonKey));
     * }
     * }
     * </pre>
     */
    public void initializeController() {
        view = View.getInstance(multitonKey, key -> new View(key));
    }

    /**
     * <P><code>Controller</code> Multiton Factory method.</P>
     *
     * @param key multitonKey
     * @param factory a factory that accepts the key and returns <code>IController</code>
     * @return the Multiton instance of <code>Controller</code>
     */
    public synchronized static IController getInstance(String key, Function<String, IController> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * <P>If an <code>ICommand</code> has previously been registered
     * to handle a the given <code>INotification</code>, then it is executed.</P>
     *
     * @param notification an <code>INotification</code>
     */
    public void executeCommand(INotification notification) {
        Supplier<ICommand> commandSupplier = commandMap.get(notification.getName());
        if(commandSupplier == null) return;
        ICommand commandInstance = commandSupplier.get();
        commandInstance.initializeNotifier(multitonKey);
        commandInstance.execute(notification);
    }

    /**
     * <P>Register a particular <code>ICommand</code> class as the handler
     * for a particular <code>INotification</code>.</P>
     *
     * <P>If an <code>ICommand</code> has already been registered to
     * handle <code>INotification</code>s with this name, it is no longer
     * used, the new <code>ICommand</code> is used instead.</P>
     *
     * <P>The Observer for the new ICommand is only created if this the
     * first time an ICommand has been regisered for this Notification name.</P>
     *
     * @param notificationName the name of the <code>INotification</code>
     * @param commandSupplier a reference to <code>ICommand</code> supplier
     */
    public void registerCommand(String notificationName, Supplier<ICommand> commandSupplier) {
        if(commandMap.get(notificationName) == null) {
            view.registerObserver(notificationName, new Observer(this::executeCommand, this));
        }
        commandMap.put(notificationName, commandSupplier);
    }

    /**
     * <P>Remove a previously registered <code>ICommand</code> to <code>INotification</code> mapping.</P>
     *
     * @param notificationName the name of the <code>INotification</code> to remove the <code>ICommand</code> mapping for
     */
    public void removeCommand(String notificationName) {
        // if the Command is registered...
        if(hasCommand(notificationName)) {
            // remove the observer
            view.removeObserver(notificationName, this);

            // remove the command
            commandMap.remove(notificationName);
        }
    }

    /**
     * <P>Check if a Command is registered for a given Notification</P>
     *
     * @param notificationName notification name
     * @return whether a Command is currently registered for the given <code>notificationName</code>.
     */
    public boolean hasCommand(String notificationName) {
        return commandMap.get(notificationName) != null;
    }

    /**
     * <P>Remove an IController instance</P>
     *
     * @param key multitonKey of IController instance to remove
     */
    public synchronized static void removeController(String key) {
        instanceMap.remove(key);
    }

}
