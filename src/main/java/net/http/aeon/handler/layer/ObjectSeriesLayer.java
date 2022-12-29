package net.http.aeon.handler.layer;

import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectSeries;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.handler.ObjectPattern;

import java.lang.reflect.Array;

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
            Aeon.instance.findPattern(element.getClass()).ifPresent(pattern -> series.add(pattern.write(element)));
        }
        return series;
    }

    @Override
    public Object read(Class<?> clazz, ObjectUnit unit) {
        if (!(unit instanceof ObjectSeries series)) throw new UnsupportedWayException();
        var array = Array.newInstance(clazz.getComponentType(), series.getUnits().size());
        for (var i = 0; i < series.getUnits().size(); i++) {
            Array.set(array, i, Aeon.instance.findPattern(clazz.getComponentType()).get().read(clazz.getComponentType(), series.getUnits().get(i)));
        }
        return array;
    }
}
