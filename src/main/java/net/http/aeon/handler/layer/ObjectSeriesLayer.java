package net.http.aeon.handler.layer;

import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectSeries;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.handler.ObjectPattern;

import java.util.Arrays;

public final class ObjectSeriesLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isArray();
    }

    @Override
    public ObjectUnit write(Object o) {

        Object[] elements;
        if (o.getClass().getComponentType().equals(int.class)) {
            elements = Arrays.stream((int[]) o).mapToObj(i -> (Object) Integer.parseInt(String.valueOf(i))).toArray();
        } else elements = (Object[]) o;

        var series = new ObjectSeries();
        for (Object element : elements) {
            Aeon.instance.findPattern(element.getClass()).ifPresent(pattern -> series.add(pattern.write(element)));
        }
        return series;
    }

    @Override
    public Object read(Class<?> clazz, ObjectUnit unit) {
        if (!(unit instanceof ObjectSeries series)) throw new UnsupportedWayException();
        var elementType = clazz.getComponentType();
        var objects = new Object[series.series().size()];
        for (var i = 0; i < series.series().size(); i++) {
            var pattern = Aeon.instance.findPattern(elementType);
            if (pattern.isPresent()) {
                objects[i] = pattern.get().read(elementType, series.series().get(i));
            }
        }

        if (clazz.equals(String[].class)) {
            return Arrays.stream(objects).map(String::valueOf).toArray(String[]::new);
        }

        if (clazz.equals(Integer[].class)) {
            return Arrays.stream(objects).map(o -> Integer.parseInt(String.valueOf(o))).toArray(Integer[]::new);
        }

        if (clazz.equals(int[].class)) {
            return Arrays.stream(objects).mapToInt(object -> (int) object).toArray();
        }


        return objects;
    }

}
