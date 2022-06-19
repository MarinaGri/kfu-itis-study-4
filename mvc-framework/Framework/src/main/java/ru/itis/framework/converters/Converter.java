package ru.itis.framework.converters;

public interface Converter<S, T> {

    T convert(S source);

}
