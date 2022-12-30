package net.http.aeon;

import org.junit.jupiter.api.Test;

public class ConfigurationTest {

    @Test
    public void handle() {
        TestConfiguration insert = Aeon.insert(new TestConfiguration());

        for (String object : insert.getCollection()) {
            System.out.println(object);
        }
    }
}
