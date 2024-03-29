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

package dev.httpmarco.aeon.io;

public abstract class DistanceElement {

    public static final String NEXT_LINE = "\n";

    private int distance = 0;

    public String space() {
        return " ".repeat(distance * 3);
    }

    public void expand() {
        distance++;
    }

    public void reduce() {
        distance--;
    }

    public void blockSet(Runnable runnable) {
        this.expand();
        runnable.run();
        this.reduce();
    }
}
