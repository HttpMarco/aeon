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

import java.lang.reflect.Type;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;
import net.http.aeon.reflections.AeonReflections;

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
