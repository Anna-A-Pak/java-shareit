package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDatesDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ItemService itemService;

    private final ObjectMapper mapper = new ObjectMapper();
    public static final String USER_HEADER = "X-Sharer-User-Id";

    private Item item1 = new Item(1, "MqERfgpUKr", "7ZQRm66h8idGlwUdpYWc74sbu6e", false);
    private ItemDto itemDto = ItemMapper.mapToDto(item1);
    private ItemWithDatesDto itemWithDate = new ItemWithDatesDto(
            2,
            "MqERfgpUKr",
            "7ZQRm66h8idGlwUdpYWc74sbu6e",
            false);
    private CommentDto commentDto = new CommentDto(
            null,
            "jdvdfhjrgrdfgjerid",
            null,
            null,
            null);

    @Test
    public void testSaveItem() throws Exception {
        when(itemService.create(1, itemDto))
                .thenReturn(itemDto);

        mockMvc.perform(post("/items")
                        .header(USER_HEADER, 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(itemDto.getName()))
                .andExpect(jsonPath("$.description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$.available").value(itemDto.getAvailable()));
    }

    @Test
    public void testUpdateItem() throws Exception {
        item1.setDescription("gftd@kfdkjsg");
        mockMvc.perform(patch("/items/1")
                        .header(USER_HEADER, 1)
                        .content(mapper.writeValueAsString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetItem() throws Exception {

        when(itemService.findById(1))
                .thenReturn(itemDto);

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(itemDto.getName()))
                .andExpect(jsonPath("$.description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$.available").value(itemDto.getAvailable()));
    }

    @Test
    public void testGetItemsByOwner() throws Exception {

        when(itemService.findAllByOwner(1))
                .thenReturn(List.of(itemWithDate));

        mockMvc.perform(get("/items")
                        .header(USER_HEADER, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(itemWithDate.getName()))
                .andExpect(jsonPath("$[0].description").value(itemWithDate.getDescription()))
                .andExpect(jsonPath("$[0].available").value(itemWithDate.getAvailable()));
    }

    @Test
    public void testSearchItems() throws Exception {
        when(itemService.searchItems("MqERf"))
                .thenReturn(List.of(itemDto));

        mockMvc.perform(get("/items/search")
                        .param("text", "MqERf"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(itemDto.getName()))
                .andExpect(jsonPath("$[0].description").value(itemDto.getDescription()))
                .andExpect(jsonPath("$[0].available").value(itemDto.getAvailable()));
    }

    @Test
    public void testCreateComment() throws Exception {
        when(itemService.createComment(commentDto, 2, 1))
                .thenReturn(commentDto);

        mockMvc.perform(post("/items/2/comment")
                        .header(USER_HEADER, 1)
                        .content(mapper.writeValueAsString(commentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(commentDto.getText()));

    }
}
