package net.http.aeon.singleton.primitives;

import net.http.aeon.Aeon;
import org.junit.jupiter.api.Test;

public class PrimitiveDataTest {

    @Test
    public void test() {

        PrimitivesConfiguration insert = Aeon.insert(new PrimitivesConfiguration());
        System.out.println(insert.getValue());

    }
}
