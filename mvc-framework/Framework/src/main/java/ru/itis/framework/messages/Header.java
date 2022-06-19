package ru.itis.framework.messages;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Header {
    private String name;
    private String value;
}
