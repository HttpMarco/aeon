package net.http.aeon.handler.layer;

import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectSeries;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;
import net.http.aeon.reflections.AeonReflections;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ObjectCollectionLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @Override
    public ObjectUnit write(Object o) {
        var series = new ObjectSeries();
        for (Object elements : (Collection<?>) o) {
            series.add(Aeon.getObjectHandler().write(elements.getClass(), elements));
        }
        return series;
    }

    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        if (!(unit instanceof ObjectSeries series)) throw new UnsupportedOperationException();
        return series.getUnits().stream().map(it -> Aeon.getObjectHandler().read(null, (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0], it)).collect(Collectors.toList());
    }
}
