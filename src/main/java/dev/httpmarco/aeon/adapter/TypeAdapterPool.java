package dev.httpmarco.aeon.adapter;

import java.lang.reflect.ParameterizedType;

import java.util.*;

public final class TypeAdapterPool {

    private final List<TypeAdapter<?>> typeAdapters = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <T> Optional<TypeAdapter<T>> findIf(Class<T> clazz) {
        return typeAdapters.stream().filter(it -> {
            Class<?> type = (Class<?>) ((ParameterizedType) it.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return type.equals(clazz)
                || (!clazz.isEnum() && findAllInterfaces(clazz).contains(type))
                || (type.getSuperclass() != null && type.getSuperclass().equals(clazz));
        }).map(typeAdapter -> (TypeAdapter<T>) typeAdapter).findFirst();
    }

    public void registerTypeAdapter(TypeAdapter<?> typeAdapter) {
        this.typeAdapters.add(typeAdapter);
    }

    public List<Class<?>> findAllInterfaces(Class<?> clazz) {
        var interfaces = new ArrayList<Class<?>>();
        for (var aClass : clazz.getInterfaces()) {
            interfaces.add(aClass);
            interfaces.addAll(findAllInterfaces(aClass));
        }
        return interfaces;
    }

}
