package net.http.aeon;

import org.junit.jupiter.api.Test;

public class ConfigurationTest {

    @Test
    public void handle() {
        TestConfiguration insert = Aeon.insert(new TestConfiguration());

        for (Byte i : insert.getTe()) {
            System.out.println(i);
        }

    }
}
