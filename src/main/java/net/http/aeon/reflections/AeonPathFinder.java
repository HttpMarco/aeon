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

import net.http.aeon.annotations.Options;

import java.nio.file.Path;

public final class AeonPathFinder {

    public static Path find(Object value) {
        final var options = value.getClass().getAnnotation(Options.class);
        if (options != null) {
            var path = Path.of(options.path()[0]);
            for (int i = 1; i < options.path().length; i++) {
              path = path.resolve(options.path()[i]);
            }
            return path
                .resolve((options.name().isEmpty() ? value.getClass().getSimpleName() : options.name()));
        }
        return Path.of(value.getClass().getSimpleName());
    }
}
