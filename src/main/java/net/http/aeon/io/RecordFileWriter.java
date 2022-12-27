package net.http.aeon.io;

import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.reflections.AeonPathFinder;
import java.io.IOException;
import java.nio.file.Files;

public final class RecordFileWriter extends DistanceElement {

    private final StringBuilder builder = new StringBuilder();

    public RecordFileWriter(Object value, ObjectUnit unit) {
        writeElement(null, unit);

        try (var reader = Files.newBufferedWriter(AeonPathFinder.find(value))) {
            reader.write(this.builder.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeElement(String key, ObjectUnit unit) {
        if (key == null && unit instanceof ObjectAssortment assortment) {
            assortment.getUnits().forEach(this::writeElement);
        } else if (unit instanceof ObjectAssortment assortment) {
            this.writeAssortment(key, assortment);
        } else if (unit instanceof ObjectPrimitive primitive) {
            this.writePrimitive(primitive, key);
        } else throw new UnsupportedWayException();
    }

    private void writeAssortment(String key, ObjectAssortment assortment) {
        this.builder.append(space()).append(key).append(": [").append(nextLine());
        this.blockSet(() -> assortment.getUnits().forEach(this::writeElement));
        this.builder.append("]").append(nextLine());
    }

    private void writePrimitive(ObjectPrimitive primitive, String key) {
        this.builder.append(space()).append(key).append(": ").append(primitive.getValue()).append(nextLine());
    }
}