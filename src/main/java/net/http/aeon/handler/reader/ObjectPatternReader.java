package net.http.aeon.handler.reader;

import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.handler.layer.ObjectAssortmentLayer;

public final class ObjectPatternReader {

    public ObjectUnit read(Object object) {
        var instancePattern = Aeon.instance.findPattern(object);

        if (instancePattern.isEmpty() || !(instancePattern.get() instanceof ObjectAssortmentLayer)) {
            throw new UnsupportedWayException();
        }
        return instancePattern.get().write(object);
    }
}
