package br.com.wsbasestructure.view.impl;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class GenericExclusionStrategy implements ExclusionStrategy{

    @Override
    public boolean shouldSkipClass(Class<?> type) {
        return type == JavassistLazyInitializer.class;
    }
    
}
