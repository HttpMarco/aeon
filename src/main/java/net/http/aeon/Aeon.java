package net.http.aeon;

import net.http.aeon.io.AeonWriter;
import net.http.aeon.pattern.PatternLayerHandler;

public final class Aeon {

    private static final PatternLayerHandler patternLayerHandler = new PatternLayerHandler();

    public static <T> T insert(T object) {
        if (!AeonHelper.isPresent(object)) {

            var section = patternLayerHandler.write(object);

            //write
            AeonWriter.write(AeonHelper.getPath(object), section);

            return object;
        }

        //TODO READ
        return null;
    }

}
