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

package net.http.aeon.io;

import net.http.aeon.annotations.CommentedArgument;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.NotImplementedYetException;
import net.http.aeon.reflections.BufferedFileInstance;

import java.nio.file.Path;
import java.util.Optional;

public final class FileInstanceWriter {

    private final BufferedFileInstance writer;

    public FileInstanceWriter(Object value, Path path, ObjectUnit unit) {
        this.writer = new BufferedFileInstance(path);
        var supportCommentedArgument = Optional.ofNullable(value.getClass().getDeclaredAnnotation(CommentedArgument.class));

        //write file header comment
        supportCommentedArgument.ifPresent(it -> {
            //add spacer before comment if present
            if (it.type().isSpacerBefore()) {
                this.writer.next();
            }

            //send more lines comment
            if (it.comment().length > 1) {
                this.writer.append("#").append(String.join("\n#", it.comment())).next();
            }
        });
        this.writeElement(unit, null);
        //writer footer spacer
        supportCommentedArgument.stream().filter(it -> it.type().isSpacerAfter()).findFirst().ifPresent(commentedArgument -> this.writer.next());
    }

    private void writeElement(ObjectUnit unit, String key) {
        if (unit instanceof ObjectAssortment assortment) {
            writeAssortment(assortment, key);
        } else if (unit instanceof ObjectPrimitive primitive) {
            writePrimitive(primitive, key);
        }
    }

    private void writeAssortment(ObjectAssortment assortment, String key) {
        if (key == null) {
            //main assortment is present
            assortment.getUnits().forEach((s, unit) -> writeElement(unit, s));
        } else {
            //todo
            //sub assortment in assortment
            throw new NotImplementedYetException();
        }
    }

    private void writePrimitive(ObjectPrimitive primitive, String key) {
        this.writer.append(key).append(": ").append(primitive.getValue().toString()).next();
    }

}
