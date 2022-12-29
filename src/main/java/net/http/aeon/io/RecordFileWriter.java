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

import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectSeries;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.reflections.AeonPathFinder;
import net.http.aeon.reflections.AeonReflections;

import java.io.IOException;
import java.nio.file.Files;

public final class RecordFileWriter extends DistanceElement {

    private final StringBuilder builder = new StringBuilder();

    public RecordFileWriter(Object value, ObjectUnit unit) {
        writeElement(null, unit, false);
        try (var reader = Files.newBufferedWriter(AeonPathFinder.find(value))) {
            reader.write(this.builder.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeElement(String key, ObjectUnit unit, boolean seriesElement) {
        if (key == null && unit instanceof ObjectAssortment assortment && !seriesElement) {
            assortment.getUnits().forEach((s, unit1) -> writeElement(s, unit1, false));
        } else if (unit instanceof ObjectAssortment assortment) {
            this.writeAssortment(key, assortment, seriesElement);
        } else if (unit instanceof ObjectSeries series) {
            this.writeSeries(key, series);
        } else if (unit instanceof ObjectPrimitive primitive) {
            this.writePrimitive(primitive, key, seriesElement);
        } else throw new UnsupportedWayException();
    }

    private void writeAssortment(String key, ObjectAssortment assortment, boolean seriesElement) {
        this.writeBlockElement(key, () -> assortment.getUnits().forEach((s, unit) -> writeElement(s, unit, false)), '[', ']', seriesElement);
    }

    private void writeSeries(String key, ObjectSeries series) {
        this.writeBlockElement(key, () -> series.getUnits().forEach(it -> writeElement(null, it, true)), '{', '}', false);
    }

    private void writePrimitive(ObjectPrimitive primitive, String key, boolean seriesElement) {
        this.builder.append(space()).append(seriesElement ? AeonReflections.EMTPY_STRING : key + ": ").append(primitive.getValue()).append(nextLine());
    }

    private void writeBlockElement(String key, Runnable handle, char openSymbol, char closeSymbol, boolean seriesElement) {
        this.builder.append(space()).append(seriesElement ? AeonReflections.EMTPY_STRING : key+ ": ").append(openSymbol).append(nextLine());
        this.blockSet(handle);
        this.builder.append(space()).append(closeSymbol).append(seriesElement ? "," : AeonReflections.EMTPY_STRING).append(nextLine());
    }
}