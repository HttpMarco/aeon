package net.http.aeon.defaults;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public final class AeonSection extends AeonElement {

    @Getter
    private Map<String, AeonElement> elements = new HashMap<>();

    public void set(String key, AeonElement aeonElement) {
        this.elements.put(key, aeonElement);
    }

}
