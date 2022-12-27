package net.http.aeon.handler.layer;

import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectSeries;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;

public final class ObjectSeriesLayer implements ObjectPattern<Object[]> {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isArray();
    }

    @Override
    public ObjectUnit write(Object o) {
        var elements = (Object[]) o;
        var series = new ObjectSeries();
        for (Object element : elements) {
            Aeon.instance.findPattern(element.getClass()).ifPresent(pattern -> series.add(pattern.write(element)));
        }
        return series;
    }

    @Override
    public Object[] read(Class<Object[]> clazz, ObjectUnit unit) {
        return new Object[0];
    }
}
