package net.http.aeon.handler.layer;

import java.lang.reflect.Type;
import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public final class ObjectMapLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    @Override
    public ObjectUnit write(Object o) {
        var map = (Map<?, ?>) o;
        var assortment = new ObjectAssortment();
        map.forEach((o1, o2) -> assortment.append(o1.toString(), Aeon.getObjectHandler().write(o2)));
        return assortment;
    }

    @Override
    public Object read(Type type, ObjectUnit unit) {
        var map = new HashMap<>();

        var typeCurrent = ((ParameterizedType) type).getActualTypeArguments()[1];

        unit.assortment().getUnits().forEach((s, unit1) -> map.put(s, Aeon.getObjectHandler()
            .read(typeCurrent, unit1)));
        return map;
    }
}
