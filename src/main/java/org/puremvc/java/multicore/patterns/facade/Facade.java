//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.facade;

import org.puremvc.java.multicore.core.Controller;
import org.puremvc.java.multicore.core.Model;
import org.puremvc.java.multicore.core.View;
import org.puremvc.java.multicore.interfaces.*;
import org.puremvc.java.multicore.patterns.observer.Notification;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Facade implements IFacade {

    // References to Model, View and Controller
    protected IController controller;
    protected IModel model;
    protected IView view;

    // The Multiton Key for this app
    protected String multitonKey;

    // The Multiton Facade instanceMap.
    protected static Map<String, IFacade> instanceMap = new HashMap<>();

    // Message Constants
    protected final String MULTITON_MSG = "Facade instance for this Multiton key already constructed!";

    /**
     * <P>Constructor.</P>
     *
     * <P>This <code>IFacade</code> implementation is a Multiton,
     * so you should not call the constructor
     * directly, but instead call the static Factory method,
     * passing the unique key for this instance
     * <code>Facade.getInstance( multitonKey )</code></P>
     *
     * @param key multitonKey
     * @throws Error Error if instance for this Multiton key has already been constructed
     *
     */
    public Facade(String key) {
        if(instanceMap.get(key) != null) throw new Error(MULTITON_MSG);
        initializeNotifier(key);
        instanceMap.put(key, this);
        initializeFacade();
    }

    /**
     * <P>Initialize the Multiton <code>Facade</code> instance.</P>
     *
     * <P>Called automatically by the constructor. Override in your
     * subclass to do any subclass specific initializations. Be
     * sure to call <code>super.initializeFacade()</code>, though.</P>
     */
    protected void initializeFacade() {
        initializeModel();
        initializeController();
        initializeView();
    }

    /**
     * <P>Facade Multiton Factory method</P>
     *
     * @param key multitonKey
     * @param facadeSupplier supplier that returns <code>IFacade</code>
     * @return the Multiton instance of the Facade
     */
    public synchronized static IFacade getInstance(String key, Supplier<IFacade> facadeSupplier) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, facadeSupplier.get());
        }
        return instanceMap.get(key);
    }

    /**
     * <P>Initialize the <code>Controller</code>.</P>
     *
     * <P>Called by the <code>initializeFacade</code> method.
     * Override this method in your subclass of <code>Facade</code>
     * if one or both of the following are true:</P>
     *
     * <UL>
     * <LI> You wish to initialize a different <code>IController</code>.</LI>
     * <LI> You have <code>Commands</code> to register with the <code>Controller</code> at startup.. </LI>
     * </UL>
     *
     * <P>If you don't want to initialize a different <code>IController</code>,
     * call <code>super.initializeController()</code> at the beginning of your
     * method, then register <code>Command</code>s.</P>
     */
    protected void initializeController() {
        if(controller != null) return;
        controller = Controller.getInstance(multitonKey, () -> new Controller(multitonKey));
    }

    /**
     * <P>Initialize the <code>Model</code>.</P>
     *
     * <P>Called by the <code>initializeFacade</code> method.
     * Override this method in your subclass of <code>Facade</code>
     * if one or both of the following are true:</P>
     *
     * <UL>
     * <LI> You wish to initialize a different <code>IModel</code>.</LI>
     * <LI> You have <code>Proxy</code>s to register with the Model that do not
     * retrieve a reference to the Facade at construction time.</LI>
     * </UL>
     *
     * <P>If you don't want to initialize a different <code>IModel</code>,
     * call <code>super.initializeModel()</code> at the beginning of your
     * method, then register <code>Proxy</code>s.</P>
     *
     * <P>Note: This method is <i>rarely</i> overridden; in practice you are more
     * likely to use a <code>Command</code> to create and register <code>Proxy</code>s
     * with the <code>Model</code>, since <code>Proxy</code>s with mutable data will likely
     * need to send <code>INotification</code>s and thus will likely want to fetch a reference to
     * the <code>Facade</code> during their construction.</P>
     */
    protected void initializeModel() {
        if(model != null) return;
        model = Model.getInstance(multitonKey, () -> new Model(multitonKey));
    }

    /**
     * <P>Initialize the <code>View</code>.</P>
     *
     * <P>Called by the <code>initializeFacade</code> method.
     * Override this method in your subclass of <code>Facade</code>
     * if one or both of the following are true:</P>
     *
     * <UL>
     * <LI> You wish to initialize a different <code>IView</code>.</LI>
     * <LI> You have <code>Observers</code> to register with the <code>View</code></LI>
     * </UL>
     *
     * <P>If you don't want to initialize a different <code>IView</code>,
     * call <code>super.initializeView()</code> at the beginning of your
     * method, then register <code>IMediator</code> instances.</P>
     *
     * <P>Note: This method is <i>rarely</i> overridden; in practice you are more
     * likely to use a <code>Command</code> to create and register <code>Mediator</code>s
     * with the <code>View</code>, since <code>IMediator</code> instances will need to send
     * <code>INotification</code>s and thus will likely want to fetch a reference
     * to the <code>Facade</code> during their construction.</P>
     */
    protected void initializeView() {
        if(view != null) return;
        view = View.getInstance(multitonKey, () -> new View(multitonKey));
    }

    /**
     * <P>Register an <code>ICommand</code> with the <code>Controller</code> by Notification name.</P>
     *
     * @param notificationName the name of the <code>INotification</code> to associate the <code>ICommand</code> with
     * @param controllerSupplier supplier that returns <code>IController</code>
     */
    public void registerCommand(String notificationName, Supplier<ICommand> controllerSupplier) {
        controller.registerCommand(notificationName, controllerSupplier);
    }

    /**
     * <P>Remove a previously registered <code>ICommand</code> to <code>INotification</code> mapping from the Controller.</P>
     *
     * @param notificationName the name of the <code>INotification</code> to remove the <code>ICommand</code> mapping for
     */
    public void removeCommand(String notificationName) {
        controller.removeCommand(notificationName);
    }

    /**
     * <P>Check if a Command is registered for a given Notification</P>
     *
     * @param notificationName notification name
     * @return whether a Command is currently registered for the given <code>notificationName</code>.
     */
    public boolean hasCommand(String notificationName) {
        return controller.hasCommand(notificationName);
    }

    /**
     * <P>Register an <code>IProxy</code> with the <code>Model</code> by name.</P>
     *
     * @param proxy the <code>IProxy</code> instance to be registered with the <code>Model</code>.
     */
    public void registerProxy(IProxy proxy) {
        model.registerProxy(proxy);
    }

    /**
     * <P>Retrieve an <code>IProxy</code> from the <code>Model</code> by name.</P>
     *
     * @param proxyName the name of the proxy to be retrieved.
     * @return the <code>IProxy</code> instance previously registered with the given <code>proxyName</code>.
     */
    public IProxy retrieveProxy(String proxyName) {
        return model.retrieveProxy(proxyName);
    }

    /**
     * <P>Remove an <code>IProxy</code> from the <code>Model</code> by name.</P>
     *
     * @param proxyName the <code>IProxy</code> to remove from the <code>Model</code>.
     * @return the <code>IProxy</code> that was removed from the <code>Model</code>
     */
    public IProxy removeProxy(String proxyName) {
        return model.removeProxy(proxyName);
    }

    /**
     * <P>Check if a Proxy is registered</P>
     *
     * @param proxyName proxy name
     * @return whether a Proxy is currently registered with the given <code>proxyName</code>.
     */
    public boolean hasProxy(String proxyName) {
        return model.hasProxy(proxyName);
    }

    /**
     * <P>Register a <code>IMediator</code> with the <code>View</code>.</P>
     *
     * @param mediator a reference to the <code>IMediator</code>
     */
    public void registerMediator(IMediator mediator) {
        view.registerMediator(mediator);
    }

    /**
     * <P>Retrieve an <code>IMediator</code> from the <code>View</code>.</P>
     *
     * @param mediatorName mediator name
     * @return the <code>IMediator</code> previously registered with the given <code>mediatorName</code>.
     */
    public IMediator retrieveMediator(String mediatorName) {
        return view.retrieveMediator(mediatorName);
    }

    /**
     * <P>Remove an <code>IMediator</code> from the <code>View</code>.</P>
     *
     * @param mediatorName name of the <code>IMediator</code> to be removed.
     * @return the <code>IMediator</code> that was removed from the <code>View</code>
     */
    public IMediator removeMediator(String mediatorName) {
        return view.removeMediator(mediatorName);
    }

    /**
     * <P>Check if a Mediator is registered or not</P>
     *
     * @param mediatorName mediator name
     * @return whether a Mediator is registered with the given <code>mediatorName</code>.
     */
    public boolean hasMediator(String mediatorName) {
        return view.hasMediator(mediatorName);
    }

    /**
     * <P>Create and send an <code>INotification</code>.</P>
     *
     * <P>Keeps us from having to construct new notification
     * instances in our implementation code.</P>
     *
     * @param notificationName the name of the notiification to send
     * @param body the body of the notification
     * @param type the type of the notification
     */
    public void sendNotification(String notificationName, Object body, String type) {
        notifyObservers(new Notification(notificationName, body, type));
    }

    /**
     * <P>Create and send an <code>INotification</code>.</P>
     *
     * <P>Keeps us from having to construct new notification
     * instances in our implementation code.</P>
     *
     * @param notificationName the name of the notiification to send
     * @param body the body of the notification
     */
    public void sendNotification(String notificationName, Object body) {
        sendNotification(notificationName, body, null);
    }

    /**
     * <P>Create and send an <code>INotification</code>.</P>
     *
     * <P>Keeps us from having to construct new notification
     * instances in our implementation code.</P>
     *
     * @param notificationName the name of the notiification to send
     */
    public void sendNotification(String notificationName) {
        sendNotification(notificationName, null, null);
    }

    /**
     * <P>Notify <code>Observer</code>s.</P>
     *
     * <P>This method is left public mostly for backward
     * compatibility, and to allow you to send custom
     * notification classes using the facade.</P>
     *
     * <P>Usually you should just call sendNotification
     * and pass the parameters, never having to
     * construct the notification yourself.</P>
     *
     * @param notification the <code>INotification</code> to have the <code>View</code> notify <code>Observers</code> of.
     */
    public void notifyObservers(INotification notification) {
        view.notifyObservers(notification);
    }

    /**
     * <P>Set the Multiton key for this facade instance.</P>
     *
     * <P>Not called directly, but instead from the
     * constructor when getInstance is invoked.
     * It is necessary to be public in order to
     * implement INotifier.</P>
     */
    public void initializeNotifier(String key) {
        multitonKey = key;
    }

    /**
     * <P>Check if a Core is registered or not</P>
     *
     * @param key the multiton key for the Core in question
     * @return whether a Core is registered with the given <code>key</code>.
     */
    public static synchronized boolean hasCore(String key) {
        return instanceMap.containsKey(key);
    }

    /**
     * <P>Remove a Core.</P>
     *
     * <P>Remove the Model, View, Controller and Facade
     * instances for the given key.</P>
     *
     * @param key of the Core to remove
     */
    public static synchronized void removeCore(String key) {
        if(instanceMap.get(key) == null) return;
        Model.removeModel(key);
        View.removeView(key);
        Controller.removeController(key);
        instanceMap.remove(key);
    }
}
