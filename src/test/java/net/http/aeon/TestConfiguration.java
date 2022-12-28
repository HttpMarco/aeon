package net.http.aeon;

import lombok.Getter;
import net.http.aeon.annotations.Options;

@Getter
@Options(path = "src/test/java/net/http/aeon/", name = "config")
public class TestConfiguration {

    private int[] te = new int[]{2, 3};
    private String name = "ad";

}
