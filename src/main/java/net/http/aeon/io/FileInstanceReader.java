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

import lombok.SneakyThrows;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.NotImplementedYetException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class FileInstanceReader {

    private final List<String> propertyLines;

    @SneakyThrows
    public FileInstanceReader(Path path) {
        this.propertyLines = Files.readAllLines(path);
    }

    public ObjectUnit read() {
        var assortment = new ObjectAssortment();
        for (var line : propertyLines) {
            //remove spaces
            line = line.trim();

            //is comment spacer & ignore comments
            if (line.isEmpty() || line.startsWith("#")) continue;

            if (line.contains(": ")) {
                if (propertyLines.contains(": [")) {
                    throw new NotImplementedYetException();
                } else {
                    var elements = line.split(": ");
                    assortment.append(elements[0], new ObjectPrimitive(elements[1]));
                }
            }
        }
        return assortment;
    }

}
