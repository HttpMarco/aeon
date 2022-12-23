package net.http.aeon.singleton.primitives;

import lombok.Getter;
import net.http.aeon.annotations.Options;

@Getter
@Options(path = "src/test/java/net/http/aeon/output/singleton/", name = "primitives-test")
public final class PrimitivesConfiguration {

    private final int value;

    public PrimitivesConfiguration() {
        this.value = 300;
    }
}
