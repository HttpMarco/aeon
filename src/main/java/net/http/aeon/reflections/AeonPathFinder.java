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

package net.http.aeon.reflections;

import net.http.aeon.Aeon;
import net.http.aeon.annotations.Options;

import java.nio.file.Files;
import java.nio.file.Path;

public final class AeonPathFinder {

    public static Path find(Object value) {
        var path = value.getClass().getSimpleName();
        if(value.getClass().isAnnotationPresent(Options.class)) {
            var opt = value.getClass().getAnnotation(Options.class);
            path = (opt.path().isEmpty() ? AeonReflections.EMTPY_STRING : opt.path()) + (opt.name().isEmpty() ? value.getClass().getSimpleName() : opt.name());
        }
        return Path.of(path + Aeon.FILE_EXTENSION);
    }

    public static boolean isPresent(Object value) {
        return Files.exists(find(value));
    }
}
