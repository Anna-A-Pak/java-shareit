package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        userRepository.checkEmail(user);
        return UserMapper.matToUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return UserMapper.mapToListUserDto(userRepository.getAllUsers());
    }

    @Override
    public UserDto findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        }
        return UserMapper.matToUserDto(userOptional.get());
    }

    @Override
    public UserDto update(Integer id, UserDto userDto) {
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
        findById(id);
        User updatedUser = UserMapper.mapToUser(userDto);
        updatedUser.setId(id);
        if (updatedUser.getEmail() != null) {
            userRepository.checkEmail(updatedUser);
        }
        return UserMapper.matToUserDto(userRepository.update(updatedUser));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        userRepository.delete(id);
    }
}
