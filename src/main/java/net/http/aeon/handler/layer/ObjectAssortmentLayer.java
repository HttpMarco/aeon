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

package net.http.aeon.handler.layer;

import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;
import net.http.aeon.reflections.AeonReflections;

import java.lang.reflect.Field;

public final class ObjectAssortmentLayer implements ObjectPattern<Object> {

    @Override
    public boolean isElement(Class<?> clazz) {
        return true;
    }

    @Override
    public ObjectUnit write(Object value) {
        var assortment = new ObjectAssortment();

        for (var field : value.getClass().getDeclaredFields()) {
            Aeon.instance.findPattern(field.getType()).ifPresent(pattern -> assortment.append(field.getName(), pattern.write(AeonReflections.get(field, value))));
        }
        return assortment;
    }

    @Override
    public Object read(Class<Object> clazz, ObjectUnit unit) {
        Object instance = AeonReflections.allocate(clazz);

        if(unit instanceof ObjectAssortment assortment) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    //todo add transformer for String to current object.
                    //noinspection unchecked
                    field.set(instance, Aeon.instance.findPattern(field.getType()).map(it -> it.read(field.getType(), assortment.get(field.getName()))).orElse(null));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return instance;
    }
}
