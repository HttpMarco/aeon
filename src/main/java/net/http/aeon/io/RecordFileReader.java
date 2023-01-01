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
import lombok.SneakyThrows;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectSeries;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.reflections.AeonPathFinder;

import java.nio.file.Files;
import java.util.List;

public final class RecordFileReader extends DistanceElement {

    @Getter
    private final ObjectAssortment objectAssortment = new ObjectAssortment();

    @SneakyThrows
    public RecordFileReader(Object object) {
        var lines = Files.readAllLines(AeonPathFinder.find(object)).stream().map(String::trim).filter(it -> !(it.isEmpty() || it.startsWith("#"))).toList();
        for (var index = 0; index < lines.size(); index++) {
            index += readElement(lines.subList(index, lines.size()), objectAssortment);
        }
    }

    private int readElement(List<String> lines, ObjectUnit unit) {
        if (lines.get(0).contains(": [")) {
            return readAssortment(lines.subList(1, lines.size()), unit, lines.get(0).split(": ")[0]);
        } else if (lines.get(0).contains(": {")) {
            return this.readSeries(lines.subList(1, lines.size()), unit, lines.get(0).split(": ")[0]);
        } else if (lines.get(0).contains(": ")) {
            return readPrimitive(unit, lines.get(0).split(": "));
        } else {
            throw new UnsupportedOperationException("Element: " + lines.get(0));
        }
    }

    private int readPrimitive(ObjectUnit unit, String[] elements) {
        if (!(unit instanceof ObjectAssortment assortment)) return 1;
        assortment.append(elements[0], new ObjectPrimitive(elements[1]));
        return 0;
    }

    private int readAssortment(List<String> lines, ObjectUnit unit, String key) {
        var id = 0;
        var instance = new ObjectAssortment();
        for (id = 0; id < lines.size(); id++) {
            if (lines.get(id).contains("]")) break;
            id += readElement(lines.subList(id, lines.size()), instance);
        }
        this.add(unit, key, instance);
        return ++id;
    }

    private int readSeries(List<String> lines, ObjectUnit unit, String key) {
        var id = 0;
        var series = new ObjectSeries();
        for (id = 0; id < lines.size(); id++) {
            var line = lines.get(id);
            if (line.contains("}")) break;
            if (line.contains("[")) {
                id += readAssortment(lines.subList(id + 1, lines.size()), series, null);
            } else {
                series.add(new ObjectPrimitive(line.substring(id, line.endsWith(",") ? line.length() - 1 : line.length())));
            }
        }
        this.add(unit, key, series);
        return ++id;
    }

    private void add(ObjectUnit unit, String key, ObjectUnit instance) {
        if (unit instanceof ObjectAssortment assortment) {
            assortment.append(key, instance);
        } else if (unit instanceof ObjectSeries series) {
            series.add(instance);
        }
    }
}