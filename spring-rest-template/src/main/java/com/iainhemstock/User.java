package com.iainhemstock;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class User {
    private int id;
    private String name;
    private String email;

    User(String name, String email) {
        this.id = 0;
        this.name = name;
        this.email = email;
    }
}
