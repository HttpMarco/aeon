package net.http.aeon.assortments.simple;

import net.http.aeon.Aeon;
import org.junit.jupiter.api.Test;

public class SimpleAssortmentTest {

    @Test
    public void handle() throws Exception {

        TestConfiguration insert = Aeon.insert(new TestConfiguration());

        System.out.println(insert.getTest());
        System.out.println(insert.getTestObject1().getElement1());



    }
}
