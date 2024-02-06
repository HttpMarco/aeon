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

package dev.httpmarco.aeon.io;

import java.util.regex.Pattern;

import dev.httpmarco.aeon.elements.ObjectAssortment;
import dev.httpmarco.aeon.elements.ObjectPrimitive;
import dev.httpmarco.aeon.elements.ObjectSeries;
import dev.httpmarco.aeon.elements.ObjectUnit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class RecordFileWriter extends DistanceElement {

    private static final Pattern PATTERN = Pattern.compile("\n");

    private final StringBuilder builder = new StringBuilder();

    public RecordFileWriter(ObjectUnit unit, Path path) {
        this.writeElement(null, unit, false);

        if (!Files.exists(path) && path.getParent() != null) {
          try {
            Files.createDirectories(path.getParent());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

        try (var reader = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            reader.write(this.builder.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeElement(String key, ObjectUnit unit, boolean seriesElement) {
        if (unit.getComments() != null) {
            for (String comment : unit.getComments()) {
                this.builder
                    .append(this.space())
                    .append("# ")
                    .append(comment)
                    .append(NEXT_LINE);
            }
        }

        if (key == null && unit instanceof ObjectAssortment assortment && !seriesElement) {
            assortment.getUnits().forEach((s, unit1) -> writeElement(s, unit1, false));
        } else if (unit == ObjectUnit.NULL) {
            this.writeNull(key, seriesElement);
        } else if (unit instanceof ObjectAssortment assortment) {
            this.writeAssortment(key, assortment, seriesElement);
        } else if (unit instanceof ObjectSeries series) {
            this.writeSeries(key, series);
        } else if (unit instanceof ObjectPrimitive primitive) {
            this.writePrimitive(primitive, key, seriesElement);
        } else throw new UnsupportedOperationException();
    }

    private void writeNull(final String key, final boolean seriesElement) {
        this.writeBasis(key, seriesElement);
        this.builder
            .append((String) null)
            .append(NEXT_LINE);
    }

    private void writeAssortment(String key, ObjectAssortment assortment, boolean seriesElement) {
        this.writeBlockElement(key, () -> assortment.getUnits().forEach((s, unit) -> writeElement(s, unit, false)), '[', ']', seriesElement);
    }

    private void writeSeries(String key, ObjectSeries series) {
        this.writeBlockElement(key, () -> {
            for (int i = 0; i < series.getUnits().size(); i++) {
                this.writeElement(null, series.getUnits().get(i), true);
                if (i < series.getUnits().size() - 1) {
                    this.builder.delete(this.builder.length() - 1, this.builder.length()).append(",\n");
                }
            }
        }, '{', '}', false);
    }

    private void writePrimitive(ObjectPrimitive primitive, String key, boolean seriesElement) {
        this.writeBasis(key, seriesElement);
        this.builder
            .append(PATTERN.matcher(primitive.getValue().toString()).replaceAll("\\\\n"))
            .append(NEXT_LINE);
    }

    private void writeBlockElement(String key, Runnable handle, char openSymbol, char closeSymbol, boolean seriesElement) {
        this.writeBasis(key, seriesElement);
        this.builder
            .append(openSymbol)
            .append(NEXT_LINE);
        this.blockSet(handle);
        this.builder.append(this.space())
            .append(closeSymbol)
            .append(NEXT_LINE);
    }

    private void writeBasis(final String key, final boolean seriesElement) {
        this.builder.append(this.space());
        if (!seriesElement) {
            this.builder.append(key).append(": ");
        }
    }
}