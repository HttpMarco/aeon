package net.http.aeon.handler;

import net.http.aeon.handler.layer.ObjectAssortmentLayer;
import net.http.aeon.handler.layer.ObjectPrimitiveLayer;
import net.http.aeon.handler.reader.ObjectPatternReader;

import java.util.Arrays;
import java.util.Optional;

public final class ObjectHandler {

    private final ObjectPattern[] patterns = new ObjectPattern[]{new ObjectPrimitiveLayer(), new ObjectAssortmentLayer()};

    // reader for objects -> units
    private final ObjectPatternReader reader;

    public ObjectHandler() {
        this.reader = new ObjectPatternReader();
    }

    public Optional<ObjectPattern> findPattern(Object object) {
        return Arrays.stream(this.patterns).filter(it -> it.isElement(object.getClass())).findFirst();
    }
}
