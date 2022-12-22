package net.http.aeon;

import net.http.aeon.annotations.PropertyOption;

import java.nio.file.Files;
import java.nio.file.Path;

public final class AeonHelper {

    public static String FILE_EXTENSION = ".aeon";

    public static String FILE_SEPARATOR = "\n";
    public static String FILE_PROPERTY = ": ";

    public static boolean isPresent(Object object) {
        return Files.exists(getPath(object));
    }

    public static Path getPath(Object object) {
        if (object.getClass().isAnnotationPresent(PropertyOption.class)) {
            var option = object.getClass().getDeclaredAnnotation(PropertyOption.class);
            return Path.of(option.path()).resolve((option.name().isEmpty() ? object.getClass().getSimpleName() : option.name()) + FILE_EXTENSION);
        }
        return Path.of(object.getClass().getSimpleName() + FILE_EXTENSION);
    }
}
