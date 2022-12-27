package net.http.aeon.transformer;

public final class StringValueTransformer implements Transformer<Object, Object> {

    @Override
    public Object handle(Class<?> type, Object transmitted) {
        var tmp = String.valueOf(transmitted);

        if (type.equals(Integer.class) || int.class.equals(type)) {
            return Integer.parseInt(tmp);
        }
        if (type.equals(Boolean.class) || boolean.class.equals(type)) {
            return Boolean.parseBoolean(tmp);
        }
        if (type.equals(Short.class) || short.class.equals(type)) {
            return Short.parseShort(tmp);
        }
        if (type.equals(Long.class) || long.class.equals(type)) {
            return Long.parseLong(tmp);
        }
        if (type.equals(Byte.class) || byte.class.equals(type)) {
            return Byte.parseByte(tmp);
        }
        if (type.equals(Double.class) || double.class.equals(type)) {
            return Double.parseDouble(tmp);
        }
        if (type.equals(Float.class) || float.class.equals(type)) {
            return Float.parseFloat(tmp);
        }
        return transmitted;
    }

}
