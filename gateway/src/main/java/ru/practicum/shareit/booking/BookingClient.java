package ru.practicum.shareit.booking;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.client.BaseClient;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> create(Integer userId, BookItemRequestDto dto) {
        return post("", userId, dto);
    }

    public ResponseEntity<Object> prove(Integer id, Boolean approved, Integer userId) {
        Map<String, Object> parameters = Map.of("approved", approved);
        return patch("/" + id + "?approved={approved}", Long.valueOf(userId), parameters, null);
    }

    public ResponseEntity<Object> getBooking(Integer id, Integer userId) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> findByBooker(String state, Integer userId) {
        Map<String, Object> parameters = Map.of("state", state);
        return get("?state={state}", Long.valueOf(userId), parameters);
    }

    public ResponseEntity<Object> findByOwner(String state, Integer userId) {
        Map<String, Object> parameters = Map.of("state", state);
        return get("?state={state}", Long.valueOf(userId), parameters);
    }
}
