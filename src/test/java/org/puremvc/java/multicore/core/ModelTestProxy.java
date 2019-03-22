//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

public class ModelTestProxy extends Proxy {

    public static final String NAME = "ModelTestProxy";
    public static final String ON_REGISTER_CALLED = "onRegister Called";
    public static final String ON_REMOVE_CALLED = "onRemove Called";

    public ModelTestProxy() {
        super(NAME, "");
    }

    @Override
    public void onRegister() {
        setData(ON_REGISTER_CALLED);
    }

    public void onRemove() {
        setData(ON_REMOVE_CALLED);
    }
}
