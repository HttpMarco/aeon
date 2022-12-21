package net.http.aeon.io;

import lombok.SneakyThrows;
import net.http.aeon.AeonHelper;
import net.http.aeon.defaults.AeonElement;
import net.http.aeon.defaults.AeonPrimitive;
import net.http.aeon.defaults.AeonSection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public final class AeonWriter {

    public static void write(Path path, AeonSection aeonSection) {
        try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            appendSection(writer, null, aeonSection);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void appendElement(Writer writer, String key, AeonElement aeonElement) {
        if (aeonElement instanceof AeonSection section) {
            appendSection(writer, key, section);
        } else if (aeonElement instanceof AeonPrimitive primitive) {
            appendPrimitives(writer, key, primitive);
        }
    }

    private static void appendSection(Writer writer, String key, AeonSection section) {
        for (final Map.Entry<String, AeonElement> entry : section.getElements().entrySet()) {
            appendElement(writer, entry.getKey(), entry.getValue());
        }
    }

    @SneakyThrows
    private static void appendPrimitives(final Writer writer, final String key, final AeonPrimitive object) {
        writer.append(key).append(": ").append(object.toString()).append(AeonHelper.FILE_SEPARATOR);
    }

}
