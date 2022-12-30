package net.http.aeon.handler.layer;

import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;

import java.util.Collection;

public class ObjectCollectionLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isAssignableFrom(Collection.class);
    }

    @Override
    public ObjectUnit write(Object o) {
        //convert to array
        return null;
    }

    @Override
    public Object read(Class<?> clazz, ObjectUnit unit) {
        //convert to array
        return null;
    }
}
