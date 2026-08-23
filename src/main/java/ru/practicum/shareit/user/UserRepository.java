package ru.practicum.shareit.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    List<User> getAllUsers();

    Optional<User> findById(Integer id);

    User update(User user);

    void delete(Integer id);

    void checkEmail(User user);
}
