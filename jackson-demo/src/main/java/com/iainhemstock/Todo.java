package com.iainhemstock;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Todo {
    private int userId;
    private int id;
    private String title;
    private boolean completed;
}
