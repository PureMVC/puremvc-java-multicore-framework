//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.mediator;

import org.puremvc.java.multicore.interfaces.IMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.observer.Notifier;

/**
 * A base <code>IMediator</code> implementation.
 *
 * @see org.puremvc.java.multicore.core.View View
 */
public class Mediator extends Notifier implements IMediator {

    /**
     * The name of the <code>Mediator</code>.
     *
     * <P>
     * Typically, a <code>Mediator</code> will be written to serve
     * one specific control or group controls and so,
     * will not have a need to be dynamically named.</P>
     */
    public static final String NAME = "Mediator";

    // the mediator name
    protected String mediatorName;

    // The view component
    protected Object viewComponent;

    /**
     * Constructor.
     *
     * @param mediatorName mediator name
     * @param viewComponent view component
     */
    public Mediator(String mediatorName, Object viewComponent) {
        this.mediatorName = mediatorName != null ? mediatorName : NAME;
        this.viewComponent = viewComponent;
    }

    /**
     * Constructor.
     *
     * @param mediatorName mediator name
     */
    public Mediator(String mediatorName) {
        this(mediatorName, null);
    }

    /**
     * Constructor.
     */
    public Mediator() {
        this(null, null);
    }

    /**
     * List the <code>INotification</code> names this
     * <code>Mediator</code> is interested in being notified of.
     *
     * @return Array the list of <code>INotification</code> names
     */
    public String[] listNotificationInterests() {
        return new String[0];
    }

    /**
     * Handle <code>INotification</code>s.
     *
     * <P>
     * Typically this will be handled in a switch statement,
     * with one 'case' entry per <code>INotification</code>
     * the <code>Mediator</code> is interested in.
     */
    public void handleNotification(INotification notification) {

    }

    /**
     * Called by the View when the Mediator is registered
     */
    public void onRegister() {

    }

    /**
     * Called by the View when the Mediator is removed
     */
    public void onRemove() {

    }

    /**
     * Get the name of the <code>Mediator</code>.
     * @return the Mediator name
     */
    public String getMediatorName() {
        return mediatorName;
    }

    /**
     * Get the <code>Mediator</code>'s view component.
     *
     * <P>
     * Additionally, an implicit getter will usually
     * be defined in the subclass that casts the view
     * object to a type, like this:</P>
     *
     * {@code
     *		public javax.swing.JComboBox getViewComponent()
     *		{
     *			return viewComponent;
     *		}
     *}
     *
     * @return the view component
     */
    public Object getViewComponent() {
        return viewComponent;
    }

    /**
     * Set the <code>IMediator</code>'s view component.
     *
     * @param viewComponent the view component
     */
    public void setViewComponent(Object viewComponent) {
        this.viewComponent = viewComponent;
    }

}
