package net.http.aeon;

import lombok.Getter;
import net.http.aeon.annotations.Options;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Options(name = "test")
public class TestConfiguration {

    @Getter
    private Map<String, String> map;

    public TestConfiguration() {
        this.map = new HashMap<>();
        this.map.put("1", "abc");
    }
}
