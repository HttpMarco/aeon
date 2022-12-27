package net.http.aeon;

import lombok.Getter;
import net.http.aeon.annotations.Options;

@Getter
@Options(path = "src/test/java/net/http/aeon/", name = "config")
public class TestConfiguration {

    private TestEnum testEnum = TestEnum.A;
    private String name = "abc";

}
