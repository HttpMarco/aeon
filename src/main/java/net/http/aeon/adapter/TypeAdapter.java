package net.http.aeon.adapter;

import net.http.aeon.handler.ObjectPattern;

public abstract class TypeAdapter implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        //ignore
        return false;
    }
}
