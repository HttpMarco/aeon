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

import lombok.NonNull;
import net.http.aeon.exceptions.NotImplementedYetException;
import net.http.aeon.handler.ObjectHandler;
import net.http.aeon.io.RecordFileReader;
import net.http.aeon.io.RecordFileWriter;
import net.http.aeon.reflections.AeonPathFinder;

@SuppressWarnings("ALL")
public final class Aeon {

    public static final String FILE_EXTENSION = ".ae";
    public static final ObjectHandler instance = new ObjectHandler();

    public static <T> T insert(@NonNull T value) {
        if(AeonPathFinder.isPresent(value)) {
            T element = (T) instance.getWriter().as(new RecordFileReader(value).getObjectAssortment(), value.getClass());
            //overwrite existing property
            new RecordFileWriter(element, instance.getReader().read(element));
            return element;
        }
        new RecordFileWriter(value, instance.getReader().read(value));
        return value;
    }

    public static void delete(@NonNull Object value) {
        throw new NotImplementedYetException();
    }

    public static <T> T update(@NonNull T value) {
        throw new NotImplementedYetException();
    }

}
