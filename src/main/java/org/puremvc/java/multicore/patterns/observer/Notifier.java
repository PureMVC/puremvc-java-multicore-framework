//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.observer;

import org.puremvc.java.multicore.interfaces.IFacade;
import org.puremvc.java.multicore.interfaces.INotifier;
import org.puremvc.java.multicore.patterns.facade.Facade;

/**
 * <P>A Base <code>INotifier</code> implementation.</P>
 *
 * <P><code>MacroCommand, Command, Mediator</code> and <code>Proxy</code>
 * all have a need to send <code>Notifications</code>.</P>
 *
 * <P>The <code>INotifier</code> interface provides a common method called
 * <code>sendNotification</code> that relieves implementation code of
 * the necessity to actually construct <code>Notifications</code>.</P>
 *
 * <P>The <code>Notifier</code> class, which all of the above mentioned classes
 * extend, provides an initialized reference to the <code>Facade</code>
 * Multiton, which is required for the convienience method
 * for sending <code>Notifications</code>, but also eases implementation as these
 * classes have frequent <code>Facade</code> interactions and usually require
 * access to the facade anyway.</P>
 *
 * <P>NOTE: In the MultiCore version of the framework, there is one caveat to
 * notifiers, they cannot send notifications or reach the facade until they
 * have a valid multitonKey.</P>
 *
 * <P>The multitonKey is set:
 *   * on a Command when it is executed by the Controller
 *   * on a Mediator is registered with the View
 *   * on a Proxy is registered with the Model.</P>
 *
 * @see org.puremvc.java.multicore.patterns.proxy.Proxy Proxy
 * @see Facade Facade
 * @see org.puremvc.java.multicore.patterns.mediator.Mediator Mediator
 * @see org.puremvc.java.multicore.patterns.command.MacroCommand MacroCommand
 * @see org.puremvc.java.multicore.patterns.command.SimpleCommand SimpleCommand
 */
public class Notifier implements INotifier {

    protected String multitonKey;

    protected final String MULTITON_MSG = "multitonKey for this Notifier not yet initialized!";

    protected IFacade getFacade() {
        if(multitonKey == null) throw new RuntimeException(MULTITON_MSG);
        return Facade.getInstance(multitonKey, key -> new Facade(key));
    }

    /**
     * <P>Create and send an <code>INotification</code>.</P>
     *
     * <P>Keeps us from having to construct new INotification
     * instances in our implementation code.</P>
     *
     * @param notificationName the name of the notiification to send
     * @param body the body of the notification
     * @param type the type of the notification
     */
    public void sendNotification(String notificationName, Object body, String type) {
        getFacade().sendNotification(notificationName, body, type);
    }

    /**
     * <P>Create and send an <code>INotification</code>.</P>
     *
     * <P>Keeps us from having to construct new INotification
     * instances in our implementation code.</P>
     *
     * @param notificationName the name of the notiification to send
     * @param body the body of the notification
     */
    public void sendNotification(String notificationName, Object body) {
        getFacade().sendNotification(notificationName, body);
    }

    /**
     * <P>Create and send an <code>INotification</code>.</P>
     *
     * <P>Keeps us from having to construct new INotification
     * instances in our implementation code.</P>
     *
     * @param notificationName the name of the notiification to send
     */
    public void sendNotification(String notificationName) {
        getFacade().sendNotification(notificationName);
    }

    /**
     * <P>Initialize this INotifier instance.</P>
     *
     * <P>This is how a Notifier gets its multitonKey.
     * Calls to sendNotification or to access the
     * facade will fail until after this method
     * has been called.</P>
     *
     * <P>Mediators, Commands or Proxies may override
     * this method in order to send notifications
     * or access the Multiton Facade instance as
     * soon as possible. They CANNOT access the facade
     * in their constructors, since this method will not
     * yet have been called.</P>
     *
     * @param key the multitonKey for this INotifier to use
     */
    public void initializeNotifier(String key) {
        multitonKey = key;
    }
}
