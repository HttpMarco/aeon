package net.http.aeon;

import lombok.NonNull;
import net.http.aeon.exceptions.NotImplementedException;
import net.http.aeon.handler.ObjectHandler;

public final class Aeon {

    public static final @NonNull ObjectHandler instance = new ObjectHandler();

    public static <T> T insert(@NonNull T value) {
        throw new NotImplementedException();
    }

    public static void delete(@NonNull Object value) {
        throw new NotImplementedException();
    }

    public static <T> T update(@NonNull T value) {
        throw new NotImplementedException();
    }
}
