package net.http.aeon.io;

import lombok.Getter;
import lombok.SneakyThrows;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.elements.ObjectSeries;
import net.http.aeon.elements.ObjectUnit;
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

    private int readElement(List<String> lines, ObjectUnit unit) {
        if (lines.get(0).contains(": [")) {
            return readAssortment(lines.subList(1, lines.size()), unit, lines.get(0).split(": ")[0]);
        } else if (lines.get(0).contains(": {")) {
            return this.readSeries(lines.subList(1, lines.size()), unit, lines.get(0).split(": ")[0]);
        } else if (lines.get(0).contains(": ")) {
            return readPrimitive(unit, lines.get(0).split(": "));
        } else {
            throw new UnsupportedWayException("Element: " + lines.get(0));
        }
    }

    private int readPrimitive(ObjectUnit unit, String[] elements) {
        if (!(unit instanceof ObjectAssortment assortment)) return 1;
        assortment.append(elements[0], new ObjectPrimitive(elements[1]));
        return 0;
    }

    private int readAssortment(List<String> lines, ObjectUnit unit, String key) {
        if (!(unit instanceof ObjectAssortment assortment)) return 1;
        var id = 0;
        var instance = new ObjectAssortment();
        for (var index = 0; index < lines.size(); index++) {
            index += readElement(lines.subList(0, lines.size()), instance);
            id = index;
            if (lines.get(id).contains("]")) break;
        }
        assortment.append(key, instance);
        return ++id;
    }

    private int readSeries(List<String> lines, ObjectUnit unit, String key) {
        var id = 0;
        var series = new ObjectSeries();
        for (var index = 0; index < lines.size(); index++) {
            var line = lines.get(index);
            id = index;

            if (line.contains("}")) break;
            series.add(new ObjectPrimitive(line.substring(0, line.length() - 1)));
        }
        if (unit instanceof ObjectAssortment assortment) {
            assortment.append(key, series);
        }
        return ++id;
    }
}