package net.http.aeon;

import lombok.Getter;
import net.http.aeon.annotations.Options;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Options(name = "test")
public class TestConfiguration {

    @Getter
    private UUID uuid;

    public TestConfiguration() {
        this.uuid = UUID.randomUUID();
    }
}
