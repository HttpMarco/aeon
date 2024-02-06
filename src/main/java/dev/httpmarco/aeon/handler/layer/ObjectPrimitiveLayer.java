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

import java.lang.reflect.Type;

import dev.httpmarco.aeon.elements.ObjectPrimitive;
import dev.httpmarco.aeon.reflections.AeonReflections;
import dev.httpmarco.aeon.elements.ObjectUnit;
import dev.httpmarco.aeon.handler.ObjectPattern;

import java.beans.PropertyEditorManager;

public final class ObjectPrimitiveLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isPrimitive() || AeonReflections.isDefaultElement(clazz);
    }

    @Override
    public ObjectUnit write(Object value) {
        return new ObjectPrimitive(value);
    }

    @Override
    public Object read(Type type, ObjectUnit unit) {
        if (!(unit instanceof ObjectPrimitive primitive)) {
            throw new UnsupportedOperationException("This is not a correct primitive type.");
        }
        var editor = PropertyEditorManager.findEditor((Class<?>) type);
        editor.setAsText(primitive.getValue().toString());
        return editor.getValue();
    }
}
