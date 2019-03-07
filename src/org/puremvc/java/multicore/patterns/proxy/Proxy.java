//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.proxy;

import org.puremvc.java.multicore.interfaces.IProxy;
import org.puremvc.java.multicore.patterns.observer.Notifier;

/**
 * A base <code>IProxy</code> implementation.
 *
 * <P>
 * In PureMVC, <code>Proxy</code> classes are used to manage parts of the
 * application's data model. </P>
 *
 * <P>
 * A <code>Proxy</code> might simply manage a reference to a local data object,
 * in which case interacting with it might involve setting and
 * getting of its data in synchronous fashion.</P>
 *
 * <P>
 * <code>Proxy</code> classes are also used to encapsulate the application's
 * interaction with remote services to save or retrieve data, in which case,
 * we adopt an asyncronous idiom; setting data (or calling a method) on the
 * <code>Proxy</code> and listening for a <code>Notification</code> to be sent
 * when the <code>Proxy</code> has retrieved the data from the service. </P>
 *
 * @see org.puremvc.java.multicore.core.Model Model
 */
public class Proxy extends Notifier implements IProxy {

    public static final String NAME = "Proxy";

    protected String proxyName;

    protected Object data;

    /**
     * Constructor
     *
     * @param proxyName proxy name
     * @param data data
     */
    public Proxy(String proxyName, Object data) {
        this.proxyName = (proxyName != null) ? proxyName : NAME;
        if(data != null) setData(data);
    }

    /**
     * Constructor
     *
     * @param proxyName proxy name
     */
    public Proxy(String proxyName) {
        this(proxyName, null);
    }

    /**
     * Constructor
     */
    public Proxy(){
        this(null, null);
    }

    /**
     * Called by the Model when the Proxy is registered
     */
    public void onRegister() {

    }

    /**
     * Called by the Model when the Proxy is removed
     */
    public void onRemove() {

    }

    /**
     * Get the proxy name
     */
    public String getProxyName() {
        return proxyName;
    }

    /**
     * Get the data object
     */
    public Object getData() {
        return data;
    }

    /**
     * Set the data object
     */
    public void setData(Object data) {
        this.data = data;
    }

}
