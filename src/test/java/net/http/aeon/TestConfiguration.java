package net.http.aeon;

import lombok.Getter;
import net.http.aeon.annotations.Options;

@Getter
@Options(path = "src/test/java/net/http/aeon/", name = "config")
public class TestConfiguration {

    private final String name;
    private final int testValue;
    private final TestObject testObject;

    public TestConfiguration() {
        this.name = "test";
        this.testValue = 22;
        this.testObject = new TestObject();
    }
}
