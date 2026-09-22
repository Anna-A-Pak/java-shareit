package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    private final UserDto userDto1 = new UserDto(1, "Ivan", "ivan@mail.mail");
    private final UserDto userDto2 = new UserDto(2, "Irina", "irinamail.mail");
    private final UserDto userDto3 = new UserDto(3, "Igor", "fsdfb@ggdjkd.ru");

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetUser() throws Exception {

        when(userService.findById(1))
                .thenReturn(userDto1);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto1.getId()))
                .andExpect(jsonPath("$.name").value(userDto1.getName()))
                .andExpect(jsonPath("$.email").value(userDto1.getEmail()));
    }

    @Test
    public void testSaveUser() throws Exception {
        when(userService.createUser(userDto1))
                .thenReturn(userDto1);

        mockMvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userDto1))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto1.getId()))
                .andExpect(jsonPath("$.name").value(userDto1.getName()))
                .andExpect(jsonPath("$.email").value(userDto1.getEmail()));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(List.of(userDto1, userDto3));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userDto1.getId()))
                .andExpect(jsonPath("$[0].name").value(userDto1.getName()))
                .andExpect(jsonPath("$[0].email").value(userDto1.getEmail()))
                .andExpect(jsonPath("$[1].id").value(userDto3.getId()))
                .andExpect(jsonPath("$[1].name").value(userDto3.getName()))
                .andExpect(jsonPath("$[1].email").value(userDto3.getEmail()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).delete(1);
    }

    @Test
    public void testUpdateUser() throws Exception {
        userDto1.setEmail("gftd@kfdkjsg.ru");
        mockMvc.perform(patch("/users/1")
                .content(mapper.writeValueAsString(userDto1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
