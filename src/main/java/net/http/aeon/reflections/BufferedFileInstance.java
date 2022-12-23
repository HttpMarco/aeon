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

import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class BufferedFileInstance  {

    private final BufferedWriter bufferedWriter;

    public BufferedFileInstance(Path path) {
        try {
            this.bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public void complete() {
        this.bufferedWriter.flush();
        this.bufferedWriter.close();
    }

    @SneakyThrows
    public BufferedFileInstance append(String component) {
        this.bufferedWriter.append(component);
        return this;
    }

    public void next() {
        this.append("\n");
    }
}
