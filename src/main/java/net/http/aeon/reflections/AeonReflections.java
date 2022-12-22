package net.http.aeon.reflections;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public final class AeonReflections {

    public static final String EMTPY_STRING = "";

    @SneakyThrows
    public static Object get(Field field, Object object) {
        return field.get(object);
    }

}
