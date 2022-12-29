package net.http.aeon.elements;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class ObjectSeries extends ObjectUnit {

    private final List<ObjectUnit> units = new ArrayList<>();

    public void add(ObjectUnit unit) {
        this.units.add(unit);
    }

}
