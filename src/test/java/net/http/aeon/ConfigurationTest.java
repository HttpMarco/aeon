package net.http.aeon;

import org.junit.jupiter.api.Test;

public class ConfigurationTest {


    private String name = "test";

    @Test
    public void handle() {

        TestConfiguration insert = Aeon.insert(new TestConfiguration());
        System.out.println(insert.getTestEnum().name());

    }
}
