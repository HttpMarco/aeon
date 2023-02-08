package net.http.aeon.handler.layer;

import lombok.SneakyThrows;
import net.http.aeon.Aeon;
import net.http.aeon.annotations.Comment;
import net.http.aeon.elements.ObjectAssortment;
import net.http.aeon.elements.ObjectUnit;
import net.http.aeon.handler.ObjectPattern;
import net.http.aeon.reflections.AeonReflections;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectRecordLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isRecord();
    }

    @Override
    public ObjectUnit write(Object value) {
        var assortment = new ObjectAssortment();
        Arrays.stream(value.getClass().getDeclaredFields()).forEach(it -> Aeon.instance.findPattern(it.getType()).ifPresent(pattern -> {
            ObjectUnit unit = pattern.write(AeonReflections.get(it, value));
            if (it.isAnnotationPresent(Comment.class)) {
                unit.setComments(it.getDeclaredAnnotation(Comment.class).comment());
            }
            assortment.append(it.getName(), unit);
        }));
        return assortment;
    }

    @SneakyThrows
    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        Class<?>[] types = Arrays.stream(clazz.getDeclaredFields()).map(Field::getType).toArray(value -> new Class<?>[value]);
        List<Object> typeObjects = new ArrayList<>();
        if (unit instanceof ObjectAssortment assortment) {
            Arrays.stream(clazz.getDeclaredFields()).forEach(it -> Aeon.instance.findPattern(it.getType()).ifPresent(pattern -> {
                if (assortment.get(it.getName()) != null) {
                    typeObjects.add(pattern.read(it.getGenericType(), it.getType(), assortment.get(it.getName())));
                }
                //TODO: add default handler
            }));
        }
        return clazz.getDeclaredConstructor(types).newInstance(typeObjects.toArray());
    }
}
