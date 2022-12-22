package net.http.aeon.handler.layer;

import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;
import net.http.aeon.reflections.AeonReflections;

public final class ObjectAssortmentLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return true;
    }

    @Override
    public ObjectUnit write(Object value) {
        var assortment = new ObjectAssortment();

        for (var field : value.getClass().getDeclaredFields()) {
            Aeon.instance.findPattern(field.getType()).ifPresent(pattern -> assortment.append(field.getName(), pattern.write(AeonReflections.get(field, value))));
        }

        return assortment;
    }
}
