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

import lombok.Getter;
import net.http.aeon.annotations.CommentedArgument;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.io.assortment.FileAssortmentSubWriter;
import net.http.aeon.reflections.AeonReflections;
import net.http.aeon.reflections.BufferedFileInstance;

import java.nio.file.Path;
import java.util.Optional;

public final class FileInstanceWriter {

    @Getter
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
            if (it.comment().length > 0) {
                this.writer.append("#").append(AeonReflections.EMTPY_STRING).append(String.join("\n#", it.comment())).next();
            }
        });
        this.writeElement(null, unit);
        //writer footer spacer
        supportCommentedArgument.stream().filter(it -> it.type().isSpacerAfter()).findFirst().ifPresent(commentedArgument -> this.writer.next());
        this.writer.complete();
    }

    private void writeElement(String key, ObjectUnit unit) {
        if (unit instanceof ObjectAssortment assortment) {
            writeAssortment(key, assortment);
        } else if (unit instanceof ObjectPrimitive primitive) {
            writePrimitive(key, primitive, 0);
        }
    }

    private void writeAssortment(String key, ObjectAssortment assortment) {
        if (key == null) {
            //main assortment is present
            assortment.getUnits().forEach(this::writeElement);
        } else {
            //sub assortment in assortment
            new FileAssortmentSubWriter(key, assortment, this, 0).handle();
        }
    }

    public void writePrimitive(String key, ObjectPrimitive primitive, int distance) {
        this.writer.append(space(distance)).append(key).append(": ").append(primitive.getValue().toString()).next();
    }

    public String space(int distance) {
        return " ".repeat(distance * 3);
    }
}
