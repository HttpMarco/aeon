package net.http.aeon;

import net.http.aeon.io.AeonReader;
import net.http.aeon.io.AeonWriter;
import net.http.aeon.pattern.PatternLayerHandler;

public final class Aeon {

    private static final PatternLayerHandler patternLayerHandler = new PatternLayerHandler();

    public static <T> T insert(T object) {
        if (!AeonHelper.isPresent(object)) {
            //write
            AeonWriter.write(AeonHelper.getPath(object), patternLayerHandler.write(object));
            return object;
        }
        //get the current object version and overwrite values
        return patternLayerHandler.read(object, AeonReader.read(AeonHelper.getPath(object)));
    }
}
