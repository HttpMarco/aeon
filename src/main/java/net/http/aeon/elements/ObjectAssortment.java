package net.http.aeon.elements;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public final class ObjectAssortment extends ObjectUnit {

    @Getter
    private final Map<String, ObjectUnit> units = new HashMap<>();

    public void append(String key, ObjectUnit unit) {
        this.units.put(key, unit);
    }

    public ObjectUnit get(String key) {
        return this.units.get(key);
    }

}
