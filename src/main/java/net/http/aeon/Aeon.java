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
import net.http.aeon.adapter.TypeAdapterPool;
import net.http.aeon.handler.ObjectHandler;
import net.http.aeon.io.RecordFileReader;
import net.http.aeon.io.RecordFileWriter;
import net.http.aeon.reflections.AeonPathFinder;

import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("ALL")
@Getter
public final class Aeon {

    private static final ObjectHandler objectHandler = new ObjectHandler();
    private static final TypeAdapterPool typeAdapterPool = new TypeAdapterPool();

    public static <T> T insert(@NonNull T value, Path path) {
        path = Path.of(path + ".ae");
        if(Files.exists(path)) {
            var element = (T) objectHandler.as(new RecordFileReader(path).getObjectAssortment(), value.getClass());
            //overwrite existing property
            new RecordFileWriter(objectHandler.read(element), path);
            return element;
        }
        new RecordFileWriter(objectHandler.read(value), path);
        return value;
    }

    public static <T> T insert(@NonNull T value) {
        return insert(value, AeonPathFinder.find(value));
    }

}
