package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailValidationException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        checkEmail(user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        }
        return userOptional.get();
    }

    @Override
    public User update(Integer id, User updatedUser) {
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
        findById(id);
        updatedUser.setId(id);
        if (updatedUser.getEmail() != null) {
            checkEmail(updatedUser);
        }
        return userRepository.update(updatedUser);
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        userRepository.delete(id);
    }

    private void checkEmail(User user) {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getEmail().contains(user.getEmail()) && !u.getId().equals(user.getId())) {
                throw new EmailValidationException("Пользователь с email = " + user.getEmail() + " уже существует");
            }
        }
    }
}
