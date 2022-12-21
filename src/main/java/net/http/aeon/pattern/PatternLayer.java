package net.http.aeon.pattern;

import net.http.aeon.defaults.AeonElement;

import java.lang.reflect.Field;

public interface PatternLayer {

    boolean isPresent(Class<?> clazz);

    AeonElement write(Field field, Object parent);

}
