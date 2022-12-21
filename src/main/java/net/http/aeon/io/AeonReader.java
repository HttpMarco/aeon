package net.http.aeon.io;

import lombok.SneakyThrows;
import net.http.aeon.defaults.AeonSection;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AeonReader {

    @SneakyThrows
    public static AeonSection read(Path path) {
        AeonSection aeonSection = new AeonSection();
        try (BufferedReader reader = Files.newBufferedReader(path)) {



        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return aeonSection;
    }


}
