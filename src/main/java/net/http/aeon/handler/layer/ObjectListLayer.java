package net.http.aeon.handler.layer;

import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;

public class ObjectListLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        //todo
        return false;
    }

    @Override
    public ObjectUnit write(Object o) {
        //todo
        return null;
    }

    @Override
    public Object read(Class<?> clazz, ObjectUnit unit) {
        //todo
        return null;
    }
}
