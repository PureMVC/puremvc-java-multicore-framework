//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

import java.util.function.Supplier;

/**
 * The interface definition for a PureMVC Facade.
 *
 * <P>
 * The Facade Pattern suggests providing a single
 * class to act as a central point of communication
 * for a subsystem. </P>
 *
 * <P>
 * In PureMVC, the Facade acts as an interface between
 * the core MVC actors (Model, View, Controller) and
 * the rest of your application.</P>
 *
 * @see org.puremvc.java.multicore.interfaces.IModel IModel
 * @see org.puremvc.java.multicore.interfaces.IView IView
 * @see org.puremvc.java.multicore.interfaces.IController IController
 * @see org.puremvc.java.multicore.interfaces.ICommand ICommand
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */
public interface IFacade extends INotifier {

    /**
     * Register an <code>IProxy</code> with the <code>Model</code> by name.
     *
     * @param proxy the <code>IProxy</code> to be registered with the <code>Model</code>.
     */
    void registerProxy(IProxy proxy);

    /**
     * Retrieve a <code>IProxy</code> from the <code>Model</code> by name.
     *
     * @param proxyName the name of the <code>IProxy</code> instance to be retrieved.
     * @return the <code>IProxy</code> previously regisetered by <code>proxyName</code> with the <code>Model</code>.
     */
    IProxy retrieveProxy(String proxyName);

    /**
     * Remove an <code>IProxy</code> instance from the <code>Model</code> by name.
     *
     * @param proxyName the <code>IProxy</code> to remove from the <code>Model</code>.
     * @return the <code>IProxy</code> that was removed from the <code>Model</code>
     */
    IProxy removeProxy(String proxyName);

    /**
     * Check if a Proxy is registered
     *
     * @param proxyName proxy name
     * @return whether a Proxy is currently registered with the given <code>proxyName</code>.
     */
    boolean hasProxy(String proxyName);

    /**
     * Register an <code>ICommand</code> with the <code>Controller</code>.
     *
     * @param notificationName the name of the <code>INotification</code> to associate the <code>ICommand</code> with.
     * @param commandSupplier a reference to <code>ICommand</code> supplier
     */
    void registerCommand(String notificationName, Supplier<ICommand> commandSupplier);

    /**
     * Remove a previously registered <code>ICommand</code> to <code>INotification</code> mapping from the Controller.
     *
     * @param notificationName the name of the <code>INotification</code> to remove the <code>ICommand</code> mapping for
     */
    void removeCommand(String notificationName);

    /**
     * Check if a Command is registered for a given Notification
     *
     * @param notificationName notification name
     * @return whether a Command is currently registered for the given <code>notificationName</code>.
     */
    boolean hasCommand(String notificationName);

    /**
     * Register an <code>IMediator</code> instance with the <code>View</code>.
     *
     * @param mediator a reference to the <code>IMediator</code> instance
     */
    void registerMediator(IMediator mediator);

    /**
     * Retrieve an <code>IMediator</code> instance from the <code>View</code>.
     *
     * @param mediatorName the name of the <code>IMediator</code> instance to retrievve
     * @return the <code>IMediator</code> previously registered with the given <code>mediatorName</code>.
     */
    IMediator retrieveMediator(String mediatorName);

    /**
     * Remove a <code>IMediator</code> instance from the <code>View</code>.
     *
     * @param mediatorName name of the <code>IMediator</code> instance to be removed.
     * @return the <code>IMediator</code> instance previously registered with the given <code>mediatorName</code>.
     */
    IMediator removeMediator(String mediatorName);

    /**
     * Check if a Mediator is registered or not
     *
     * @param mediatorName mediator name
     * @return whether a Mediator is registered with the given <code>mediatorName</code>.
     */
    boolean hasMediator(String mediatorName);

    /**
     * Notify <code>Observer</code>s.
     * <P>
     * This method is left public mostly for backward
     * compatibility, and to allow you to send custom
     * notification classes using the facade.</P>
     *<P>
     * Usually you should just call sendNotification
     * and pass the parameters, never having to
     * construct the notification yourself.</P>
     *
     * @param notification the <code>INotification</code> to have the <code>View</code> notify <code>Observers</code> of.
     */
    void notifyObservers(INotification notification);
}
