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

package dev.httpmarco.aeon.handler.layer;

import dev.httpmarco.aeon.annotations.Comment;
import dev.httpmarco.aeon.elements.ObjectAssortment;
import dev.httpmarco.aeon.elements.ObjectUnit;
import dev.httpmarco.aeon.handler.ObjectPattern;
import dev.httpmarco.aeon.reflections.AeonReflections;
import dev.httpmarco.aeon.Aeon;

import java.lang.reflect.Type;

public final class ObjectAssortmentLayer implements ObjectPattern {

    public static ObjectAssortmentLayer INSTANCE;

    public ObjectAssortmentLayer() {
        INSTANCE = this;
    }

    @Override
    public boolean isElement(Class<?> clazz) {
        return true;
    }

    @Override
    public ObjectUnit write(Object value) {
        var assortment = new ObjectAssortment();
        for (final var field : value.getClass().getDeclaredFields()) {
            var unit = Aeon.getObjectHandler().write(AeonReflections.get(field, value));
            if (field.isAnnotationPresent(Comment.class)) {
                unit.setComments(field.getDeclaredAnnotation(Comment.class).comment());
            }
            assortment.append(field.getName(), unit);
        }
        return assortment;
    }

    @Override
    public Object read(Type type, ObjectUnit unit) {
        final var clazz = (Class<?>) type;
        var object = AeonReflections.allocate(clazz);
        if (unit instanceof ObjectAssortment assortment) {
            for (final var field : clazz.getDeclaredFields()) {
                if (assortment.has(field.getName())) {
                    AeonReflections.modify(field, object, Aeon.getObjectHandler()
                        .read(field.getGenericType(), assortment.get(field.getName())));
                } else {
                    AeonReflections.modify(field, object, null);
                }
            }
        }
        return object;
    }
}
