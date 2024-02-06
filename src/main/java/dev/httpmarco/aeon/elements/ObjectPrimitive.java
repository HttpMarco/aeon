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

package dev.httpmarco.aeon.elements;

import dev.httpmarco.aeon.reflections.AeonReflections;
import lombok.AllArgsConstructor;
import lombok.Getter;
import dev.httpmarco.aeon.Aeon;

@Getter
@AllArgsConstructor
public final class ObjectPrimitive extends ObjectUnit {

    private Object value;

    public String asString() {
        return value.toString();
    }

    public int asInt() {
        return Integer.parseInt(value.toString());
    }

    public boolean asBoolean() {
        return Boolean.parseBoolean(value.toString());
    }

    public double asDouble() {
        return Double.parseDouble(value.toString());
    }

    public short asShort() {
        return Short.parseShort(value.toString());
    }

    public float asFloat() {
        return Float.parseFloat(value.toString());
    }

    public <T> T as(Class<T> clazz) {
        if (clazz.isPrimitive() || AeonReflections.isDefaultElement(clazz)) {
            return (T) this.value;
        } else {
            return (T) Aeon.getObjectHandler().read(clazz, this);
        }
    }
}
