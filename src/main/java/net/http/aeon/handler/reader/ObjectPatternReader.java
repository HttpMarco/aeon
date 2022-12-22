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

package net.http.aeon.handler.reader;

import net.http.aeon.Aeon;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.handler.layer.ObjectAssortmentLayer;

public final class ObjectPatternReader {

    public ObjectUnit read(Object object) {
        var instancePattern = Aeon.instance.findPattern(object.getClass());

        if (instancePattern.isEmpty() || !(instancePattern.get() instanceof ObjectAssortmentLayer)) {
            throw new UnsupportedWayException();
        }

        return instancePattern.get().write(object);
    }
}
