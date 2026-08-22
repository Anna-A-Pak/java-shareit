package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User findById(Integer id);

    User update(Integer id, User updatedUser);

    void delete(Integer id);
}
