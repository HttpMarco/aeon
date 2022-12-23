package net.http.aeon.io.assortment;

import lombok.Getter;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.exceptions.AeonFormattingException;
import net.http.aeon.io.FileInstanceReader;

import java.util.List;

public final class FileAssortmentSubReader {

    private List<String> nextLines;
    @Getter
    private int index = 0;

    public FileAssortmentSubReader(List<String> nextLines) {
        this.nextLines = nextLines;
    }

    public ObjectAssortment read(FileInstanceReader reader) {
        var objectAssortment = new ObjectAssortment();

        for (int index = 0; index < nextLines.size(); index++) {
            var line = nextLines.get(index);
            if (line.contains(": ")) {
                reader.readPrimitives(line, objectAssortment);
            } else if (line.contains(": [")) {
                var element = new FileAssortmentSubReader(nextLines.subList(index, nextLines.size()));
                objectAssortment.append(line.split(": ")[0], element.read(reader));
                index += element.getIndex();
            } else if (line.contains("]")) {
                break;
            } else throw new AeonFormattingException("Reader found an unknown formatting: {aeon: " + line + "}");
        }
        return objectAssortment;
    }
}
