package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto findById(Integer id);

    UserDto update(Integer id, UserDto userDto);

    void delete(Integer id);
}
