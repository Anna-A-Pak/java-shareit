package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestWithItemsDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    public static final String USER_HEADER = "X-Sharer-User-Id";
    private final ItemRequestService requestService;


    @PostMapping
    public ResponseEntity<ItemRequestDto> create(@Valid @RequestBody ItemRequestDto dto,
                                                 @RequestHeader (USER_HEADER) Integer userId) {
        return ResponseEntity.ok(requestService.create(dto, userId));
    }

    @GetMapping
    public ResponseEntity<List<RequestWithItemsDto>> getAllByRequester(@RequestHeader(USER_HEADER) Integer userId) {
        return ResponseEntity.ok(requestService.findAllByRequester(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllRequests(@RequestHeader(USER_HEADER) Integer userId) {
        return ResponseEntity.ok(requestService.findAllRequests(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestWithItemsDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(requestService.findById(id));
    }
}
