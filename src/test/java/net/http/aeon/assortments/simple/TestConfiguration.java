package net.http.aeon.assortments.simple;

import lombok.Getter;
import net.http.aeon.annotations.Options;
import net.http.aeon.assortments.complex.TestObject1;

@Getter
@Options(path = "src/test/java/net/http/aeon/output/assortments/simple/", name = "assortment-test")
public final class TestConfiguration {

    private String test = "polo";

    @Getter
    private TestObject1 testObject1 = new TestObject1();

}
