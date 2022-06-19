package ru.itis.framework.converters;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class EntityToJsonConverter implements EntityToStringConverter {

    private final Gson gson;

    public EntityToJsonConverter() {
        this.gson = new Gson();
    }

    @Override
    public String convert(Object source) {
        return gson.toJson(source);
    }

}
