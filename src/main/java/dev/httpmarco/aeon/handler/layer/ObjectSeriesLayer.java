package dev.httpmarco.aeon.handler.layer;

import dev.httpmarco.aeon.elements.ObjectSeries;
import dev.httpmarco.aeon.elements.ObjectUnit;
import dev.httpmarco.aeon.Aeon;
import dev.httpmarco.aeon.handler.ObjectPattern;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

public final class ObjectSeriesLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isArray();
    }

    @Override
    public ObjectUnit write(Object o) {
        var series = new ObjectSeries();
        for (var i = 0; i < Array.getLength(o); i++) {
            var element = Array.get(o, i);
            series.add(Aeon.getObjectHandler().write(element));
        }
        return series;
    }

    @Override
    public Object read(Type type, ObjectUnit unit) {
        final var clazz = (Class<?>) type;
        if (!(unit instanceof ObjectSeries series)) throw new UnsupportedOperationException();
        var array = Array.newInstance(clazz.getComponentType(), series.getUnits().size());
        for (var i = 0; i < series.getUnits().size(); i++) {
            Array.set(array, i, Aeon.getObjectHandler().read(clazz.getComponentType(), series.getUnits().get(i)));
        }
        return array;
    }
}
