package net.http.aeon.adapter.extras;

import net.http.aeon.adapter.TypeAdapter;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;

import java.util.UUID;

public final class UUIDTypeAdapter extends TypeAdapter<UUID> {

    @Override
    public ObjectUnit write(UUID value) {
        return new ObjectPrimitive(value.toString());
    }

    @Override
    public UUID read(Class<UUID> value, ObjectUnit unit) {
        return UUID.fromString(unit.primitives().getValue().toString());
    }
}
