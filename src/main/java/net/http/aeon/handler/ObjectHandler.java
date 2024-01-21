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

package net.http.aeon.handler;

import java.lang.reflect.ParameterizedType;
import lombok.Getter;
import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.layer.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

@Getter
public final class ObjectHandler {

    private final ObjectPattern[] patterns = new ObjectPattern[]{
            new ObjectSeriesLayer(), new ObjectCollectionLayer(), new ObjectEnumerationLayer(), new ObjectPrimitiveLayer(),
            new ObjectMapLayer(), new ObjectRecordLayer(), new ObjectAssortmentLayer(),
    };

    public <T> ObjectUnit write(T object) {
        if (object == null) {
            return ObjectUnit.NULL;
        }
        @SuppressWarnings("unchecked")
        final var clazz = (Class<T>) object.getClass();
        final var adapter = Aeon.getTypeAdapterFactory().getTypeAdapterPool().findIf(clazz);
        if (adapter.isPresent()) {
            return adapter.get().writeInstance(object);
        } else {
            var optional = Aeon.getObjectHandler().findPattern(clazz);
            if (optional.isEmpty()) {
                return ObjectUnit.NULL;
            }
            return optional.get().write(object);
        }
    }


    public Object read(Type type, ObjectUnit unit) {
        if (unit == ObjectUnit.NULL) {
            return null;
        }
        Class<?> clazz = null;
        if (type instanceof Class<?>) {
            clazz = (Class<?>) type;
        } else if (type instanceof ParameterizedType parameterizedType) {
            clazz = (Class<?>) parameterizedType.getRawType();
        }

        final var adapter = Aeon.getTypeAdapterFactory()
            .getTypeAdapterPool().findIf(clazz);
        if (adapter.isPresent()) {
            try {
                return adapter.get().readInstance(clazz, unit);
            } catch (Exception exception) {
               // return adapter.get(clazz).readCaughtException(exception);
                exception.printStackTrace();
                return null;
            }
        } else {
            return Aeon.getObjectHandler().findPattern(clazz)
                .map(objectPattern -> objectPattern.read(type, unit))
                .orElse(null);
        }
    }

    public Optional<ObjectPattern> findPattern(Class<?> clazz) {
        return Arrays.stream(this.patterns).filter(it -> it.isElement(clazz)).findFirst();
    }
}