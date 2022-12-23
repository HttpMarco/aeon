package net.http.aeon.handler.reader;

import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.handler.layer.ObjectAssortmentLayer;

public final class ObjectPatternWriter {

    public <T> T as(ObjectUnit objectUnit, Class<T> clazz) {
        var instancePattern = Aeon.instance.findPattern(clazz);

        if (instancePattern.isEmpty() || !(instancePattern.get() instanceof ObjectAssortmentLayer)) {
            throw new UnsupportedWayException();
        }

        return (T) instancePattern.get().read(clazz, objectUnit);
    }
}
