package net.http.aeon.adapter;

import java.util.*;

public final class TypeAdapterPool {

    private final List<TypeAdapter> typeAdapters = new ArrayList<>();

    public boolean isPresent(Class<?> clazz) {
        return typeAdapters.stream().anyMatch(it -> it.isElement(clazz));
    }

    public TypeAdapter get(Class<?> clazz) {
        return typeAdapters.stream().filter(it -> it.isElement(clazz)).findFirst().orElse(null);
    }

    public void registerTypeAdapter(TypeAdapter typeAdapter) {
        this.typeAdapters.add(typeAdapter);
    }
}
