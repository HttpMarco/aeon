package net.http.aeon;

import net.http.aeon.reflections.AeonReflections;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ConfigurationTest {

    @Test
    public void handle() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        TestRecord record = new TestRecord("test", 0);

        System.out.println(record.getClass().isRecord());

        for (Field field : record.getClass().getDeclaredFields()) {
            System.out.println(field.getName());
        }

        TestRecord instance = record.getClass().getDeclaredConstructor(Arrays.stream(record.getClass().getDeclaredFields()).map(field -> field.getType()).toArray(value -> {
            return new Class<?>[value];
        })).newInstance(null, 3);

        System.out.println(instance.type());



        /*
        TestConfiguration insert = Aeon.insert(new TestConfiguration());

        for (String object : insert.getCollection()) {
            System.out.println(object);
        }
         */
    }
}
