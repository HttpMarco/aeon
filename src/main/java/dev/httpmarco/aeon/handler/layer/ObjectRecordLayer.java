package dev.httpmarco.aeon.handler.layer;

import java.lang.reflect.Type;

import dev.httpmarco.aeon.elements.ObjectAssortment;
import dev.httpmarco.aeon.elements.ObjectUnit;
import lombok.SneakyThrows;
import dev.httpmarco.aeon.Aeon;
import dev.httpmarco.aeon.handler.ObjectPattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public final class ObjectRecordLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isRecord();
    }

    @Override
    public ObjectUnit write(Object value) {
        return ObjectAssortmentLayer.INSTANCE.write(value);
    }

    @SneakyThrows
    @Override
    public Object read(Type type, ObjectUnit unit) {
        final var clazz = (Class<?>) type;
        var types = Arrays.stream(clazz.getDeclaredFields())
            .map(Field::getType)
            .toArray(value -> new Class<?>[value]);
        var typeObjects = new ArrayList<>();
        if (unit instanceof ObjectAssortment assortment) {
            for (final var field : clazz.getDeclaredFields()) {
                if (assortment.get(field.getName()) != null) {
                    typeObjects.add(Aeon.getObjectHandler()
                        .read(field.getGenericType(), assortment.get(field.getName())));
                }
            }
        }
        Constructor<?> constructor = clazz.getDeclaredConstructor(types);
        constructor.setAccessible(true);
        return constructor.newInstance(typeObjects.toArray());
    }
}
