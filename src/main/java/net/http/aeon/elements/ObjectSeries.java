package net.http.aeon.elements;

import java.util.ArrayList;
import java.util.List;

public final class ObjectSeries extends ObjectUnit {

    private final List<ObjectUnit> unitSeries = new ArrayList<>();

    public void add(ObjectUnit unit) {
        this.unitSeries.add(unit);
    }

    public List<ObjectUnit> series() {
        return unitSeries;
    }
}
