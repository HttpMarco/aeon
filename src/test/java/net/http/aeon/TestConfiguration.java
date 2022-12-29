package net.http.aeon;

import lombok.Getter;
import net.http.aeon.annotations.Options;

@Getter
@Options(path = "src/test/java/net/http/aeon/", name = "config")
public class TestConfiguration {

    private Byte[] te = new Byte[]{0, 2};

}
