package net.http.aeon.io;

import lombok.SneakyThrows;
import net.http.aeon.defaults.AeonSection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AeonReader {

    @SneakyThrows
    public static AeonSection read(Path path) {
        var aeonSection = new AeonSection();
        try (var reader = Files.newBufferedReader(path)) {

            String line;
            var lastProperty = "";
            while ((line = reader.readLine()) != null) {

                line = removeSpaces(line);

                if(isComment(line)) continue;




            }


        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return aeonSection;
    }

    private static boolean isComment(String line) {
        return line.startsWith("#");
    }

    private static String removeSpaces(String line) {
        if (line.startsWith(" ")) {
            var firstSpace = 0;
            final var chars = line.toCharArray();
            for (var i = 0; i < chars.length; i++) {
                if (chars[i] != ' ') {
                    firstSpace = i;
                    break;
                }
            }
            line = line.substring(firstSpace);
        }
        return line;
    }

}
