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
        if (key == null && unit instanceof ObjectAssortment assortment) {
            assortment.getUnits().forEach((s, unit1) -> writeElement(s, unit1, seriesElement));
        } else if (unit instanceof ObjectAssortment assortment) {
            this.writeAssortment(key, assortment, seriesElement);
        } else if (unit instanceof ObjectSeries series) {
            this.writeSeries(key, series);
        } else if (unit instanceof ObjectPrimitive primitive) {
            this.writePrimitive(primitive, key, seriesElement);
        } else throw new UnsupportedWayException();
    }

    private void writeAssortment(String key, ObjectAssortment assortment, boolean seriesElement) {
        this.builder.append(space()).append(key).append(": [").append(nextLine());
        this.blockSet(() -> assortment.getUnits().forEach((s, unit) -> writeElement(s, unit, seriesElement)));
        this.builder.append(space()).append("]").append(nextLine());
    }

    private void writeSeries(String key, ObjectSeries series) {
        this.builder.append(space()).append(key).append(": {").append(nextLine());
        this.blockSet(() -> series.series().forEach(it -> writeElement(null, it, true)));
        this.builder.append(space()).append("}").append(nextLine());
    }

    private void writePrimitive(ObjectPrimitive primitive, String key, boolean seriesElement) {
        this.builder.append(space()).append(seriesElement ? "" : key + ": ").append(primitive.getValue()).append(seriesElement ? "," + nextLine() : nextLine());
    }
}