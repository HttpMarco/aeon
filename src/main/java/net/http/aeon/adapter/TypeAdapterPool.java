package net.http.aeon.adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class TypeAdapterPool {

    private final Map<Class<?>, TypeAdapter> typeAdapters = new HashMap<>();

    public boolean isPresent(Class<?> clazz) {
        return typeAdapters.containsKey(clazz) || (Arrays.stream(clazz.getInterfaces()).anyMatch(typeAdapters::containsKey));
    }

    public TypeAdapter get(Class<?> clazz) {
        return typeAdapters.get(clazz);
    }

    public void registerTypeAdapter(TypeAdapter typeAdapter, Class<?>... clazz) {
        for (Class<?> aClass : clazz) {
            this.typeAdapters.put(aClass, typeAdapter);
        }
    }
}
