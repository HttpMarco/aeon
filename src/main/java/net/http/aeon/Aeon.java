/*
 * Copyright 2022 Aeon contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.http.aeon;

import lombok.Getter;
import lombok.NonNull;
import net.http.aeon.adapter.TypeAdapterFactory;
import net.http.aeon.adapter.TypeAdapterPool;
import net.http.aeon.annotations.Options;
import net.http.aeon.handler.ObjectHandler;
import net.http.aeon.io.RecordFileReader;
import net.http.aeon.io.RecordFileWriter;
import net.http.aeon.reflections.AeonPathFinder;

import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("ALL")
public final class Aeon {

    @Getter
    private static final ObjectHandler objectHandler = new ObjectHandler();
    @Getter
    private static final TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactory();

    public static <T> T insert(@NonNull T value, Path path) {
        if (value.getClass().isAnnotationPresent(Options.class)) {
            Options options = value.getClass().getDeclaredAnnotation(Options.class);
            if (options.name().length() > 0) {
                path = path.resolve(Path.of(options.name()));
            }
        }
        path = Path.of(path + ".ae");

        if (Files.exists(path)) {
            var element = (T) objectHandler.read(null, value.getClass(), new RecordFileReader(path).getObjectAssortment());
            //overwrite existing property
            new RecordFileWriter(objectHandler.write(value.getClass(), element), path);
            return element;
        }
        new RecordFileWriter(objectHandler.write(value.getClass(), value), path);
        return value;
    }

    public static <T> T insert(@NonNull T value) {
        return insert(value, AeonPathFinder.find(value));
    }

}
