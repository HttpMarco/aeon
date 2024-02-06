package dev.httpmarco.aeon.handler.layer;

import dev.httpmarco.aeon.elements.ObjectSeries;
import dev.httpmarco.aeon.elements.ObjectUnit;
import dev.httpmarco.aeon.Aeon;
import dev.httpmarco.aeon.handler.ObjectPattern;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
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
            series.add(Aeon.getObjectHandler().write(elements));
        }
        return series;
    }

    @Override
    public Object read(Type type, ObjectUnit unit) {
        if (!(unit instanceof ObjectSeries series)) throw new UnsupportedOperationException();
        return series.getUnits().stream().map(it -> Aeon.getObjectHandler()
            .read(((ParameterizedType) type).getActualTypeArguments()[0], it))
            .collect(Collectors.toList());
    }
}
