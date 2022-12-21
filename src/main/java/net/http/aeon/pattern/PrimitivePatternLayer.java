package net.http.aeon.pattern;

import lombok.SneakyThrows;
import net.http.aeon.defaults.AeonElement;
import net.http.aeon.defaults.AeonPrimitive;

import java.lang.reflect.Field;

public final class PrimitivePatternLayer implements PatternLayer {


    @Override
    public boolean isPresent(Class<?> clazz) {
        return clazz.isPrimitive();
    }

    @Override
    @SneakyThrows
    public AeonElement write(Field field, Object parent) {
        return new AeonPrimitive(field.get(parent));
    }
}
