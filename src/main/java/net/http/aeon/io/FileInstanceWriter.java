package net.http.aeon.io;

import lombok.SneakyThrows;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.exceptions.NotImplementedYetException;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileInstanceWriter {

    private final Writer writer;

    public static FileInstanceWriter write(Path path, ObjectUnit unit) {
        try (var bufferedWriter = Files.newBufferedWriter(path)) {
            return new FileInstanceWriter(bufferedWriter, unit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileInstanceWriter(Writer writer, ObjectUnit unit) {
        this.writer = writer;
        this.writeElement(unit, null);
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

    @SneakyThrows
    private void writePrimitive(ObjectPrimitive primitive, String key) {
        writer.append(key).append(": ").append(primitive.getValue().toString()).append("\n");
    }
}
