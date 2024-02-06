package dev.httpmarco.aeon.adapter.extras;

import dev.httpmarco.aeon.elements.ObjectPrimitive;
import dev.httpmarco.aeon.elements.ObjectUnit;
import dev.httpmarco.aeon.adapter.TypeAdapter;

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
