package net.http.aeon.adapter;

import net.http.aeon.elements.ObjectUnit;

public abstract class TypeAdapter<T> {

    public ObjectUnit writeInstance(T value) {
        return this.write(value);
    }

    @SuppressWarnings("unchecked")
    public Object readInstance(Class<?> value, ObjectUnit unit) {
        return this.read((Class<T>) value, unit);
    }

    public abstract ObjectUnit write(T value);

    public abstract T read(Class<T> value, ObjectUnit unit);

}
