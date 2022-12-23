package net.http.aeon.assortments.complex;

import net.http.aeon.annotations.Options;

@Options(path = "src/test/java/net/http/aeon/output/assortments/", name = "assortment-test")
public final class TestProperty {

    private final TestObject1 testObject1 = new TestObject1();
    private final TestObject2 testObject2 = new TestObject2();

}
