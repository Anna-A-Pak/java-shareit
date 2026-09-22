package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;


@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {

    public static final String USER_HEADER = "X-Sharer-User-Id";
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody RequestDto dto,
                                                 @RequestHeader(USER_HEADER) Integer userId) {
        return requestClient.create(userId, dto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllByRequester(@RequestHeader(USER_HEADER) Integer userId) {
        return requestClient.getAllByRequester(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(USER_HEADER) Integer userId) {
        return requestClient.getAllRequests(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRequest(@PathVariable Integer id) {
        return requestClient.getRequest(id);
    }
}
