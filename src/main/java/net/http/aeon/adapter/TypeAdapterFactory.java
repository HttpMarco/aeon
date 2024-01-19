package net.http.aeon.adapter;

import lombok.Getter;
import net.http.aeon.adapter.extras.UUIDTypeAdapter;

@Getter
public final class TypeAdapterFactory {

    private final TypeAdapterPool typeAdapterPool = new TypeAdapterPool();

    public TypeAdapterFactory() {

        //default uuid type adapter
        this.typeAdapterPool.registerTypeAdapter(new UUIDTypeAdapter());
    }
}
