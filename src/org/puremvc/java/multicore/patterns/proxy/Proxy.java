/* 
 PureMVC Java MultiCore Port by Matthieu Mauny <matthieu.mauny@puremvc.org> 
 PureMVC - Copyright(c) 2006-08 Futurescale, Inc., Some rights reserved. 
 Your reuse is governed by the Creative Commons Attribution 3.0 License 
 */
package org.puremvc.java.multicore.patterns.proxy;

import org.puremvc.java.multicore.interfaces.IProxy;
import org.puremvc.java.multicore.patterns.observer.Notifier;

/**
 * A base <code>IProxy</code> implementation.
 * 
 * <P>
 * In PureMVC, <code>Proxy</code> classes are used to manage parts of the
 * application's data model.
 * </P>
 * 
 * <P>
 * A <code>Proxy</code> might simply manage a reference to a local data
 * object, in which case interacting with it might involve setting and getting
 * of its data in synchronous fashion.
 * </P>
 * 
 * <P>
 * <code>Proxy</code> classes are also used to encapsulate the application's
 * interaction with remote services to save or retrieve data, in which case, we
 * adopt an asyncronous idiom; setting data (or calling a method) on the
 * <code>Proxy</code> and listening for a <code>Notification</code> to be
 * sent when the <code>Proxy</code> has retrieved the data from the service.
 * </P>
 * 
 * @see org.puremvc.java.core.model.Model Model
 */
public class Proxy extends Notifier implements IProxy
{

	// the proxy name
	private String proxyName = "Proxy";

	// the data object
	private Object data = null;

	/**
	 * Constructor
	 * @param proxyName 
	 * @param data 
	 */
	public Proxy( String proxyName, Object data )
	{
		if (proxyName != null) {
			this.proxyName = proxyName;
		}
		if (data != null) {
			this.data = data;
		}
	}
	
	/**
	 * Constructor
	 * @param proxyName 
	 */
	public Proxy( String proxyName)
	{
		if (proxyName != null) {
			this.proxyName = proxyName;
		}
	}

	/**
	 * Get the proxy name
	 * @return the proxy name
	 */
	public String getProxyName( )
	{
		return this.proxyName;
	}

	/**
	 * Set the data object
	 * @param data 
	 */
	public void setData( Object data )
	{
		this.data = data;
	}

	/**
	 * Get the data object
	 * @return the data object
	 */
	public Object getData( )
	{
		return this.data;
	}
	
	/**
	 * Called by the Model when the Proxy is registered
	 */ 
	public void onRegister( )
	{
		
	}
	
	/**
	 * Called by the Model when the Proxy is removed
	 */ 
	public void onRemove( ) {}
}
