/*
 * Copyright 2022 Aeon contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.http.aeon.reflections;

import java.util.List;
import lombok.SneakyThrows;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public final class AeonReflections {

    @SuppressWarnings("sunapi")
    private static final Unsafe unsafe;
    private static final List<Class<?>> ELEMENTS = List.of(String.class, Integer.class,
        Boolean.class, Short.class, Float.class, Byte.class, Double.class, Long.class);

    static {
        try {
            var field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static<T> T allocate(Class<T> tClass) {
        try {
            return (T) unsafe.allocateInstance(tClass);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modify(Field field, Object object, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDefaultElement(Class<?> type) {
        return ELEMENTS.contains(type);
    }

    @SneakyThrows
    public static Object get(Field field, Object object) {
        field.setAccessible(true);
        return field.get(object);
    }
}
