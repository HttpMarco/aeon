package net.http.aeon.handler.layer;

import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;

public final class ObjectAssortmentLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return true;
    }

    @Override
    public ObjectUnit write(Object o) {
        return null;
    }
}
