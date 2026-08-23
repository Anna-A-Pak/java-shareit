package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.EmailValidationException;

import java.util.*;

@Component
public class UserRepositoryImp implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User update(User user) {
        User updatedUser = users.get(user.getId());
        if (user.getName() != null) {
            updatedUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            updatedUser.setEmail(user.getEmail());
        }
        return updatedUser;
    }

    @Override
    public void delete(Integer id) {
        users.remove(id);
    }

    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public void checkEmail(User user) {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getEmail().contains(user.getEmail()) && !u.getId().equals(user.getId())) {
                throw new EmailValidationException("Пользователь с email = " + user.getEmail() + " уже существует");
            }
        }
    }
}
