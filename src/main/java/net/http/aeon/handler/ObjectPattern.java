package net.http.aeon.handler;

import net.http.aeon.elements.ObjectUnit;

public interface ObjectPattern {

    boolean isElement(Class<?> clazz);

    ObjectUnit write(Object o);

}
