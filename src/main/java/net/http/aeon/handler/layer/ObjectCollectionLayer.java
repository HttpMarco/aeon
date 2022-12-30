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
        return clazz.isAssignableFrom(Collection.class);
    }

    @Override
    public ObjectUnit write(Object o) {
        var collection = (Collection<?>) o;
        var series = new ObjectSeries();
        for (Object elements : collection) {
            Aeon.instance.findPattern(elements.getClass()).ifPresent(pattern -> series.add(pattern.write(elements)));
        }
        return series;
    }

    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        //convert to array
        if (!(unit instanceof ObjectSeries series)) throw new UnsupportedOperationException();
        Class<?> typeClass = (Class<?>) ( (ParameterizedType) type).getActualTypeArguments()[0];
        List<Object> collect = series.getUnits().stream().map(it -> Aeon.instance.findPattern(typeClass).get().read(null, typeClass, it)).collect(Collectors.toList());
        return collect;
    }
}
