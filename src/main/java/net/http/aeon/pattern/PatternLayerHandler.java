/*
 * Copyright 2022 Aeon team & contributors
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

package net.http.aeon.pattern;

import net.http.aeon.defaults.AeonSection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public final class PatternLayerHandler {

    private final PatternLayer[] layers = new PatternLayer[]{
            //layer for only primitive datatype
            new PrimitivePatternLayer()
    };

    public AeonSection write(Object object) {
        var aeonSection = new AeonSection();
        for (var field : findFields(object)) {

            //search for availed pattern
            findPattern(field).ifPresentOrElse(patternLayer -> {
                field.setAccessible(true);
                aeonSection.set(field.getName(), patternLayer.write(field, object));
            }, () -> System.err.println("Could not find a schema for the '" + field.getName() + "' parameter."));
        }
        return aeonSection;
    }

    public <T> T read(T object, AeonSection section) {

        //TODO: overwrite

        return object;
    }

    private Optional<PatternLayer> findPattern(Field field) {
        return Arrays.stream(this.layers).filter(it -> it.isPresent(field.getType())).findFirst();
    }

    private Field[] findFields(Object object) {
        return object.getClass().getDeclaredFields();
    }
}
