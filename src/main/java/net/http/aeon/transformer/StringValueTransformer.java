package net.http.aeon.transformer;

public final class StringValueTransformer implements Transformer<Object, Object> {

    @Override
    public Object handle(Class<?> type, Object transmitted) {
        if(type.equals(String.class)) return transmitted;
        var tmp = String.valueOf(transmitted);

        //todo: find a better way <3

        //integer handling
        if (type.equals(Integer.class) || int.class.equals(type)) {
            return Integer.parseInt(tmp);
        }

        //boolean handling
        if (type.equals(Boolean.class) || boolean.class.equals(type)) {
            return Boolean.parseBoolean(tmp);
        }

        //short handling
        if (type.equals(Short.class) || short.class.equals(type)) {
            return Short.parseShort(tmp);
        }

        return tmp;
    }
}
