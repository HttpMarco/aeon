package net.http.aeon.adapter;

import lombok.Getter;
import net.http.aeon.adapter.extras.UUIDTypeAdapter;

import java.util.UUID;

public final class TypeAdapterFactory {

    @Getter
    private final TypeAdapterPool typeAdapterPool = new TypeAdapterPool();

    public TypeAdapterFactory() {

        //default uuid type adapter
        this.typeAdapterPool.registerTypeAdapter(new UUIDTypeAdapter());

    }
}
