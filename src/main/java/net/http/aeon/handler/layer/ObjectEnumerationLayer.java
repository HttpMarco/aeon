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
        if (!(o instanceof Enum<?> object)) throw new UnsupportedWayException("The given object is not an enumeration");
        return new ObjectPrimitive(object.name());
    }

    @Override
    public T read(Class<T> clazz, ObjectUnit unit) {
        if (unit instanceof ObjectPrimitive primitive) {
            try {
                return (T) Enum.valueOf(clazz, primitive.getValue().toString().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception) {
                var constants = clazz.getEnumConstants();
                var notPresentConstant = "Enum constant is not present: " + primitive.getValue().toString().toUpperCase(Locale.ROOT);
                if (constants.length == 0) {
                    throw new UnsupportedWayException(notPresentConstant + ", no default value is present.");
                }
                System.out.println(notPresentConstant + " <-> change to default value: " + constants[0].name());
                return constants[0];
            }
        }
        throw new UnsupportedWayException("The given unit is not an enumeration");
    }
}
