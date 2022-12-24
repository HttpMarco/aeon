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
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;
import net.http.aeon.reflections.AeonReflections;
import net.http.aeon.transformer.StringValueTransformer;
import net.http.aeon.transformer.Transformer;

public final class ObjectAssortmentLayer implements ObjectPattern<Object> {

    private Transformer<Object, Object> transformer = new StringValueTransformer();

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
        var instance = AeonReflections.allocate(clazz);
        if (unit instanceof ObjectAssortment assortment) {
            for (var field : clazz.getDeclaredFields()) {

                var filedUnit = assortment.get(field.getName());

                Aeon.instance.findPattern(field.getType()).ifPresent(pattern -> {
                    var readableObject = pattern.read(field.getType(), filedUnit);

                    if (filedUnit instanceof ObjectPrimitive && !field.getType().isEnum()) {
                        readableObject = transformer.handle(field.getType(), readableObject);
                    }

                    try {
                        field.setAccessible(true);
                        field.set(instance, readableObject);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        return instance;
    }
}
