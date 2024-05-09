package io.huyvu.hicha.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    private Long userId;
    private String username;

}
