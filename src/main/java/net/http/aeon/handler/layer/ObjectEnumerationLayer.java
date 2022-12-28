package net.http.aeon.handler.layer;

import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.handler.ObjectPattern;
import java.util.Locale;

public final class ObjectEnumerationLayer implements ObjectPattern {

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
    public Object read(Class<?> clazz, ObjectUnit unit) {
        if (unit instanceof ObjectPrimitive primitive) {
            Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;
            try {
                return Enum.valueOf(enumClass, primitive.getValue().toString().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception) {
                var constants = enumClass.getEnumConstants();
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
