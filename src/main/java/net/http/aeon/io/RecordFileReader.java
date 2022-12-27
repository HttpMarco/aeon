package net.http.aeon.io;

import lombok.Getter;
import lombok.SneakyThrows;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.exceptions.UnsupportedWayException;
import net.http.aeon.reflections.AeonPathFinder;

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
            return readAssortment(lines.subList(1, lines.size()), assortment);
        } else if (lines.get(0).contains(": ")) {
            return readPrimitive(assortment, lines.get(1).split(": "));
        } else throw new UnsupportedWayException();
    }

    public int readPrimitive(ObjectAssortment assortment, String[] elements) {
        assortment.append(elements[0], new ObjectPrimitive(elements[1]));
        return 1;
    }

    public int readAssortment(List<String> lines, ObjectAssortment assortment) {
        var id = 0;
        for (var key : assortment.getUnits().keySet()) {

        }
        if (lines.get(id).contains("]")) {
            System.out.println("polo");
        }
        return ++id;
    }
}