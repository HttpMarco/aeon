package net.http.aeon;

import net.http.aeon.adapter.extras.UUIDTypeAdapter;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ConfigurationTest {

    @Test
    public void handle()  {

        Aeon.getTypeAdapterFactory().getTypeAdapterPool().registerTypeAdapter(new UUIDTypeAdapter(), UUID.class);

        TestConfiguration insert = Aeon.insert(new TestConfiguration());
        System.out.println(insert.getUuid());
    }
}
