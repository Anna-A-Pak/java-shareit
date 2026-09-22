package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestWithItemsDto;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
public class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ItemRequestService requestService;

    private final ObjectMapper mapper = new ObjectMapper();
    public static final String USER_HEADER = "X-Sharer-User-Id";

    private ItemRequestDto dto = new ItemRequestDto("jksdfchsdhj");
    private RequestWithItemsDto dtoWithItems = new RequestWithItemsDto("chjsdfjhsdgjgdfh", new ArrayList<>());

    @Test
    public void testCreateRequest() throws Exception {
        when(requestService.create(dto, 1))
                .thenReturn(dto);

        mockMvc.perform(post("/requests")
                        .header(USER_HEADER, 1)
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(dto.getDescription()));
    }

    @Test
    public void testGetAllByRequester() throws Exception {

        when(requestService.findAllByRequester(1))
                .thenReturn(List.of(dtoWithItems));

        mockMvc.perform(get("/requests")
                        .header(USER_HEADER, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value(dtoWithItems.getDescription()))
                .andExpect(jsonPath("$[0].items").value(dtoWithItems.getItems()));
    }

    @Test
    public void testGetAllRequests() throws Exception {

        when(requestService.findAllRequests(1))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/requests/all")
                        .header(USER_HEADER, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value(dto.getDescription()));
    }

    @Test
    public void testGetRequest() throws Exception {

        when(requestService.findById(1))
                .thenReturn(dtoWithItems);

        mockMvc.perform(get("/requests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(dtoWithItems.getDescription()));
    }
}
