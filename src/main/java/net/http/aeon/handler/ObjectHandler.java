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

import lombok.Getter;
import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.layer.*;

import java.util.Arrays;
import java.util.Optional;

@Getter
@SuppressWarnings({"unchecked"})
public final class ObjectHandler {

    private final ObjectPattern[] patterns = new ObjectPattern[]{new ObjectSeriesLayer(), new ObjectCollectionLayer(), new ObjectEnumerationLayer(), new ObjectPrimitiveLayer(), new ObjectAssortmentLayer()};

    public Optional<ObjectPattern> findPattern(Class<?> clazz) {
        return Arrays.stream(this.patterns).filter(it -> it.isElement(clazz)).findFirst();
    }

    public ObjectUnit read(Object object) {
        return findPossiblePattern(object.getClass()).write(object);
    }

    public <T> T as(ObjectUnit objectUnit, Class<T> clazz) {
        return (T) findPossiblePattern(clazz).read(null, clazz, objectUnit);
    }

    private ObjectPattern findPossiblePattern(Class<?> clazz) {
        var pattern = Aeon.instance.findPattern(clazz);
        if (pattern.isEmpty() || !(pattern.get() instanceof ObjectAssortmentLayer)) throw new UnsupportedOperationException();
        return pattern.get();
    }
}