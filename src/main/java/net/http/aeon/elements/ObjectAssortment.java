package net.http.aeon.elements;

import java.util.HashMap;
import java.util.Map;

public final class ObjectAssortment extends ObjectUnit {

    private final Map<String, ObjectUnit> units = new HashMap<>();

    public void append(String key, ObjectUnit unit) {
        this.units.put(key, unit);
    }

    public ObjectUnit get(String key) {
        return this.units.get(key);
    }

}
