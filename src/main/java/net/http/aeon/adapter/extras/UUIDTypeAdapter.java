package net.http.aeon.adapter.extras;

import net.http.aeon.adapter.TypeAdapter;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;

import java.lang.reflect.Type;
import java.util.UUID;

public final class UUIDTypeAdapter extends TypeAdapter {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.equals(UUID.class);
    }

    @Override
    public ObjectUnit write(Object o) {
        return new ObjectPrimitive(o.toString());
    }

    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        return UUID.fromString(unit.primitives().getValue().toString());
    }
}
