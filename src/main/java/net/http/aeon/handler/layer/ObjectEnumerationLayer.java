package net.http.aeon.handler.layer;

import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.handler.ObjectPattern;

import java.util.Locale;

public final class ObjectEnumerationLayer<T extends Enum> implements ObjectPattern<T> {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isEnum();
    }

    @Override
    public ObjectUnit write(Object o) {
        if(!(o instanceof Enum<?> object)) throw new UnsupportedWayException("The given object is not an enumeration");
        return new ObjectPrimitive(object.name());
    }

    @Override
    public T read(Class<T> clazz, ObjectUnit unit) {
        if(unit instanceof ObjectPrimitive primitive) {
            return (T) Enum.valueOf(clazz, primitive.getValue().toString().toUpperCase(Locale.ROOT));
        }
        throw new UnsupportedWayException("The given unit is not an enumeration");
    }
}
