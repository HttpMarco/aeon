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
import lombok.Setter;

@Setter
@Getter
public abstract class ObjectUnit {

    public static final ObjectUnit NULL = new Null();

    private String[] comments;

    public ObjectAssortment assortment() {
        return (ObjectAssortment) this;
    }

    public ObjectPrimitive primitives() {
        return (ObjectPrimitive) this;
    }

    public ObjectSeries series() {
        return (ObjectSeries) this;
    }

    private static final class Null extends ObjectUnit {
    }

}
