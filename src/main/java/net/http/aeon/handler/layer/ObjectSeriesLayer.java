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
        if (clazz.equals(Boolean[].class)) {
            return Arrays.stream(objects).map(o -> Boolean.parseBoolean(String.valueOf(o))).toArray(Boolean[]::new);
        }
        if (clazz.equals(Short[].class)) {
            return Arrays.stream(objects).map(o -> Short.parseShort(String.valueOf(o))).toArray(Short[]::new);
        }
        if (clazz.equals(Byte[].class)) {
            return Arrays.stream(objects).map(o -> Byte.parseByte(String.valueOf(o))).toArray(Byte[]::new);
        }
        if (clazz.equals(Float[].class)) {
            return Arrays.stream(objects).map(o -> Float.parseFloat(String.valueOf(o))).toArray(Float[]::new);
        }
        if (clazz.equals(Double[].class)) {
            return Arrays.stream(objects).map(o -> Double.parseDouble(String.valueOf(o))).toArray(Double[]::new);
        }
        if (clazz.equals(Long[].class)) {
            return Arrays.stream(objects).map(o -> Long.parseLong(String.valueOf(o))).toArray(Long[]::new);
        }


        //primitives
        if (clazz.equals(int[].class)) {
            return Arrays.stream(objects).mapToInt(object -> (int) object).toArray();
        }
        if (clazz.equals(boolean[].class)) {
            return Arrays.stream(objects).map(object -> (boolean) object).toArray();
        }
        if (clazz.equals(short[].class)) {
            return Arrays.stream(objects).map(object -> (short) object).toArray();
        }
        if (clazz.equals(byte[].class)) {
            return Arrays.stream(objects).map(object -> (byte) object).toArray();
        }
        if (clazz.equals(float[].class)) {
            return Arrays.stream(objects).map(object -> (float) object).toArray();
        }
        if (clazz.equals(double[].class)) {
            return Arrays.stream(objects).map(object -> (double) object).toArray();
        }
        if (clazz.equals(long[].class)) {
            return Arrays.stream(objects).map(object -> (long) object).toArray();
        }

        //default value
        return objects;
    }
}
