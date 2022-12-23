package net.http.aeon.io.assortment;

import lombok.AllArgsConstructor;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectPrimitive;
import net.http.aeon.io.FileInstanceWriter;

@AllArgsConstructor
public final class FileAssortmentSubWriter {

    private final String key;
    private final ObjectAssortment assortment;
    private final FileInstanceWriter fileInstanceWriter;
    private int distance;

    public void handle() {
        var writer = fileInstanceWriter.getWriter();
        writer.append(this.fileInstanceWriter.space(distance)).append(key).append(": ").append("[").append("\n");

        distance++;

        assortment.getUnits().forEach((s, unit) -> {
            if(unit instanceof ObjectAssortment objectAssortment) {
                new FileAssortmentSubWriter(s, objectAssortment, this.fileInstanceWriter, distance).handle();
            }else if(unit instanceof ObjectPrimitive primitive) {
                this.fileInstanceWriter.writePrimitive(s, primitive, distance);
            }
        });

        //close assortment
        distance--;
        writer.append(this.fileInstanceWriter.space(distance)).append("]").append("\n");
    }
}
