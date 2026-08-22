package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;


@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User createdUser = userService.createUser(user);
        UserDto createdUserDto = UserMapper.matToUserDto(createdUser);
        return ResponseEntity.ok(createdUserDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(UserMapper.mapToListUserDto(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(UserMapper.matToUserDto(userService.findById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> update(@Valid @PathVariable Integer id, @RequestBody UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        return ResponseEntity.ok(UserMapper.matToUserDto(userService.update(id, user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
