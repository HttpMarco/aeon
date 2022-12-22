package net.http.aeon;

import lombok.NonNull;
import net.http.aeon.exceptions.NotImplementedYetException;
import net.http.aeon.handler.ObjectHandler;
import net.http.aeon.reflections.AeonPathFinder;

public final class Aeon {

    public static final String FILE_EXTENSION = ".ae";
    public static final @NonNull ObjectHandler instance = new ObjectHandler();

    public static <T> T insert(@NonNull T value) {

        if(AeonPathFinder.isPresent(value)) {
            //todo read, override,
            return value;
        }

        var unit = instance.getReader().read(value);
        //todo write in file

        return value;
    }

    public static void delete(@NonNull Object value) {
        throw new NotImplementedYetException();
    }

    public static <T> T update(@NonNull T value) {
        throw new NotImplementedYetException();
    }

}
