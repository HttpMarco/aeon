package net.http.aeon.transformer;

public interface Transformer<T, R> {

    T handle(Class<?> type, R transmitted);

}
