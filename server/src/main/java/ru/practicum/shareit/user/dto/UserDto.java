package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Integer id;
    private String name;
    private String email;

    public UserDto(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
