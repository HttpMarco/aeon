package net.http.aeon.elements;

public abstract class ObjectUnit {

    public ObjectAssortment assortment() {
        return (ObjectAssortment) this;
    }

    public ObjectPrimitive primitives() {
        return (ObjectPrimitive) this;
    }

}
