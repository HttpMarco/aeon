package dev.httpmarco.aeon.handler.layer;

import java.lang.reflect.Type;

import dev.httpmarco.aeon.elements.ObjectPrimitive;
import dev.httpmarco.aeon.elements.ObjectUnit;
import dev.httpmarco.aeon.handler.ObjectPattern;

import java.util.Locale;

public final class ObjectEnumerationLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isEnum();
    }

    @Override
    public ObjectUnit write(Object object) {
        return new ObjectPrimitive(((Enum<?>) object).name());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object read(Type type, ObjectUnit unit) {
        if (unit instanceof ObjectPrimitive primitive) {
            var enumClass = (Class<? extends Enum>) type;
            try {
                return Enum.valueOf(enumClass, primitive.getValue().toString().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception) {
                var constants = enumClass.getEnumConstants();
                var notPresentConstant = "Enum constant is not present: " + primitive.getValue().toString().toUpperCase(Locale.ROOT);
                if (constants.length == 0) {
                    throw new UnsupportedOperationException(notPresentConstant + ", no default value is present.");
                }
                System.out.println(notPresentConstant + " <-> change to default value: " + constants[0].name());
                return constants[0];
            }
        }
        throw new UnsupportedOperationException("The given unit is not an enumeration");
    }
}
