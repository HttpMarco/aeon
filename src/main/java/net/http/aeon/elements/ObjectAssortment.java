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

package net.http.aeon.elements;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public final class ObjectAssortment extends ObjectUnit {

    @Getter
    private final Map<String, ObjectUnit> units = new HashMap<>();

    public void append(String key, ObjectUnit unit) {
        this.units.put(key, unit);
    }

    public ObjectUnit get(String key) {
        return this.units.get(key);
    }

    public boolean has(String key) {
        return this.units.containsKey(key);
    }
}
