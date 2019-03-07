//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.observer;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.interfaces.IObserver;

import java.util.function.Consumer;

/**
 * A base <code>IObserver</code> implementation.
 *
 * <P>
 * An <code>Observer</code> is an object that encapsulates information
 * about an interested object with a method that should
 * be called when a particular <code>INotification</code> is broadcast. </P>
 *
 * <P>
 * In PureMVC, the <code>Observer</code> class assumes these responsibilities:
 * <UL>
 * <LI>Encapsulate the notification (callback) method of the interested object.</LI>
 * <LI>Encapsulate the notification context (this) of the interested object.</LI>
 * <LI>Provide methods for setting the notification method and context.</LI>
 * <LI>Provide a method for notifying the interested object.</LI>
 * </UL>
 *
 * @see org.puremvc.java.multicore.core.View View
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notification
 */
public class Observer implements IObserver {

    private Object context;
    private Consumer<INotification> notify;

    /**
     * Constructor.
     *
     * <P>
     * The notification method on the interested object should take
     * one parameter of type <code>INotification</code></P>
     *
     * @param notifyMethod the notification method of the interested object
     * @param notifyContext the notification context of the interested object
     */
    public Observer(Consumer<INotification> notifyMethod, Object notifyContext) {
        setNotifyMethod(notifyMethod);
        setNotifyContext(notifyContext);
    }

    /**
     * Compare an object to the notification context.
     *
     * @param object the object to compare
     * @return boolean indicating if the object and the notification context are the same
     */
    public boolean compareNotifyContext(Object object) {
        return object == this.context;
    }

    /**
     * Notify the interested object.
     *
     * @param notification the <code>INotification</code> to pass to the interested object's notification method.
     */
    public void notifyObserver(INotification notification) {
        notify.accept(notification);
    }

    /**
     * Get the notification context.
     *
     * @return the notification context (<code>this</code>) of the interested object.
     */
    protected Object getNotifyContext() {
        return context;
    }

    /**
     * Set the notification context.
     *
     * @param notifyContext the notification context (this) of the interested object.
     */
    public void setNotifyContext(Object notifyContext) {
        this.context = notifyContext;
    }

    /**
     * Get the notification method.
     *
     * @return the notification consumer of the interested object.
     */
    protected Consumer<INotification> getNotifyMethod() {
        return notify;
    }

    /**
     * Set the notification method.
     *
     * <P>
     * The notification method should take one parameter of type <code>INotification</code>.</P>
     *
     * @param notify the notification (callback) method of the interested object.
     */
    public void setNotifyMethod(Consumer<INotification> notify) {
        this.notify = notify;
    }

}
