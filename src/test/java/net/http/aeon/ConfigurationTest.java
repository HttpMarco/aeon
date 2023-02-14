package net.http.aeon;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class ConfigurationTest {

    @Test
    public void handle() {
        TestConfiguration insert = Aeon.insert(new TestConfiguration(), Path.of("polo"));
        System.out.println(insert.getUuid());
    }
}
