package net.http.aeon.io;

import lombok.Getter;
import lombok.SneakyThrows;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.reflections.AeonPathFinder;

import java.io.BufferedReader;
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

    public int readElement(List<String> lines, ObjectAssortment assortment) {
        if (lines.get(0).contains(": [")) {
            return readAssortment(lines.subList(1, lines.size()), assortment, lines.get(0).split(": ")[0]);
        } else if (lines.get(0).contains(": ")) {
            return readPrimitive(assortment, lines.get(0).split(": "));
        } else throw new UnsupportedWayException();
    }

    public int readPrimitive(ObjectAssortment assortment, String[] elements) {
        assortment.append(elements[0], new ObjectPrimitive(elements[1]));
        return 0;
    }

    public int readAssortment(List<String> lines, ObjectAssortment assortment, String key) {
        var id = 0;
        ObjectAssortment instance = new ObjectAssortment();
        for (var index = 0; index < lines.size(); index++) {
            index += readElement(lines.subList(0, lines.size()), instance);
            id = index;
            if(lines.get(id).contains("]")) break;
        }
        assortment.append(key, instance);
        return ++id;
    }
}