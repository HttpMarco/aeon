package net.http.aeon.defaults;

public final class AeonPrimitive extends AeonElement {

    private final Object value;

    public AeonPrimitive(Object value) {
        this.value = value;
    }

    public String toString() {
        return this.value.toString();
    }
}
