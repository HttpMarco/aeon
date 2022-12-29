package net.http.aeon;

import lombok.Getter;
import net.http.aeon.annotations.Options;

@Getter
@Options(path = "src/test/java/net/http/aeon/", name = "config")
public class TestConfiguration {

    private TestObject[] testObjects = new TestObject[]{new TestObject(), new TestObject()};

}
