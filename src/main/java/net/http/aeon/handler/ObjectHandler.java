package net.http.aeon.handler;

import lombok.Getter;
import net.http.aeon.handler.layer.ObjectAssortmentLayer;
import net.http.aeon.handler.layer.ObjectPrimitiveLayer;
import net.http.aeon.handler.reader.ObjectPatternReader;
import java.util.Arrays;
import java.util.Optional;

public final class ObjectHandler {

    private final ObjectPattern[] patterns = new ObjectPattern[]{new ObjectPrimitiveLayer(), new ObjectAssortmentLayer()};

    @Getter
    private final ObjectPatternReader reader;

    public ObjectHandler() {
        this.reader = new ObjectPatternReader();
    }

    public Optional<ObjectPattern> findPattern(Class<?> clazz) {
        return Arrays.stream(this.patterns).filter(it -> it.isElement(clazz)).findFirst();
    }

}
