package net.http.aeon;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class ConfigurationTest {

    @Test
    public void handle() {
        TestConfiguration insert = Aeon.insert(new TestConfiguration(), Path.of("polo"));

        insert.getMap().forEach((s, s2) -> {
            System.out.println(s);
        });
    }
}
