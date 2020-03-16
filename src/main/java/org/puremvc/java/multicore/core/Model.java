//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.interfaces.IModel;
import org.puremvc.java.multicore.interfaces.IProxy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * <P>A Multiton <code>IModel</code> implementation.</P>
 *
 * <P>In PureMVC, the <code>Model</code> class provides
 * access to model objects (Proxies) by named lookup.</P>
 *
 * <P>The <code>Model</code> assumes these responsibilities:</P>
 *
 * <UL>
 * <LI>Maintain a cache of <code>IProxy</code> instances.</LI>
 * <LI>Provide methods for registering, retrieving, and removing
 * <code>IProxy</code> instances.</LI>
 * </UL>
 *
 * <P>Your application must register <code>IProxy</code> instances
 * with the <code>Model</code>. Typically, you use an
 * <code>ICommand</code> to create and register <code>IProxy</code>
 * instances once the <code>Facade</code> has initialized the Core
 * actors.</p>
 *
 * @see org.puremvc.java.multicore.patterns.proxy.Proxy Proxy
 * @see org.puremvc.java.multicore.interfaces.IProxy IProxy
 */

public class Model implements IModel {

    // The Multiton Key for this Core
    protected String multitonKey;

    // Mapping of proxyNames to IProxy instances
    protected ConcurrentMap<String, IProxy> proxyMap;

    // The Multiton Model instanceMap.
    protected static Map<String, IModel> instanceMap = new HashMap<>();

    // Message Constants
    protected final String MULTITON_MSG = "Model instance for this Multiton key already constructed!";

    /**
     * <P>Constructor.</P>
     *
     * <P>This <code>IModel</code> implementation is a Multiton,
     * so you should not call the constructor
     * directly, but instead call the static Multiton</P>
     *
     * Factory method {@code Model.getInstance(multitonKey, () -> new Model(multitonKey))}
     *
     * @param key multitonKey
     * @throws Error Error if instance for this Multiton key instance has already been constructed
     */
    public Model(String key) {
        if(instanceMap.get(key) != null) throw new Error(MULTITON_MSG);
        multitonKey = key;
        instanceMap.put(key, this);
        proxyMap = new ConcurrentHashMap<>();
        initializeModel();
    }

    /**
     * <P>Initialize the <code>Model</code> instance.</P>
     *
     * <P>Called automatically by the constructor, this
     * is your opportunity to initialize the Singleton
     * instance in your subclass without overriding the
     * constructor.</P>
     */
    protected void initializeModel() {
    }

    /**
     * <P><code>Model</code> Multiton Factory method.</P>
     *
     * @param key multitonKey
     * @param factory a factory that accepts the key and returns <code>IModel</code>
     * @return the Multiton instance of <code>Model</code>
     */
    public synchronized static IModel getInstance(String key, Function<String, IModel> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * <P>Register an <code>IProxy</code> with the <code>Model</code>.</P>
     *
     * @param proxy an <code>IProxy</code> to be held by the <code>Model</code>.
     */
    public void registerProxy(IProxy proxy) {
        proxy.initializeNotifier(multitonKey);
        proxyMap.put(proxy.getProxyName(), proxy);
        proxy.onRegister();
    }

    /**
     * <P>Retrieve an <code>IProxy</code> from the <code>Model</code>.</P>
     *
     * @param proxyName proxy name
     * @return the <code>IProxy</code> instance previously registered with the given <code>proxyName</code>.
     */
    public IProxy retrieveProxy(String proxyName) {
        return proxyMap.get(proxyName);
    }

    /**
     * <P>Check if a Proxy is registered</P>
     *
     * @param proxyName proxy name
     * @return whether a Proxy is currently registered with the given <code>proxyName</code>.
     */
    public boolean hasProxy(String proxyName) {
        return proxyMap.containsKey(proxyName);
    }

    /**
     * <P>Remove an <code>IProxy</code> from the <code>Model</code>.</P>
     *
     * @param proxyName name of the <code>IProxy</code> instance to be removed.
     * @return the <code>IProxy</code> that was removed from the <code>Model</code>
     */
    public IProxy removeProxy(String proxyName) {
        IProxy proxy = proxyMap.get(proxyName);
        if(proxy != null) {
            proxyMap.remove(proxyName);
            proxy.onRemove();
        }
        return proxy;
    }

    /**
     * <P>Remove an IModel instance</P>
     *
     * @param key of IModel instance to remove
     */
    public synchronized static void removeModel(String key) {
        instanceMap.remove(key);
    }

}
