package net.http.aeon;

import lombok.Getter;

import java.util.UUID;

public class TestConfiguration {

    @Getter
    private UUID uuid;

    public TestConfiguration() {
        this.uuid = UUID.randomUUID();
    }
}
