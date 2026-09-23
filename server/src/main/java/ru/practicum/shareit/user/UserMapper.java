package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@UtilityClass
public class UserMapper {
    public static UserDto matToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }

    public static List<UserDto> mapToListUserDto(List<User> users) {
        return users
                .stream()
                .map(UserMapper::matToUserDto)
                .toList();
    }
}
