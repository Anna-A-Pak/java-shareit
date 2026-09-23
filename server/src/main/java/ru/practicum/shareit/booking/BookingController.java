package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.RequestHeaders;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> create(@RequestBody NewBookingDto newBookingDto,
                                             @RequestHeader(RequestHeaders.USER_HEADER) Integer userId) {
        return ResponseEntity.ok(bookingService.create(userId, newBookingDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingDto> prove(@PathVariable Integer id,
                                            @RequestParam Boolean approved,
                                            @RequestHeader(RequestHeaders.USER_HEADER) Integer userId) {
        return ResponseEntity.ok(bookingService.prove(id, approved, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> findById(@PathVariable Integer id,
                                               @RequestHeader(RequestHeaders.USER_HEADER) Integer userId) {
        return ResponseEntity.ok(bookingService.findById(id, userId));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> findByBooker(@RequestParam(defaultValue = "ALL") String state,
                                                    @RequestHeader(RequestHeaders.USER_HEADER) Integer userId) {
        return ResponseEntity.ok(bookingService.findByBooker(state, userId));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingDto>> findByOwner(@RequestParam(defaultValue = "ALL") String state,
                                                         @RequestHeader(RequestHeaders.USER_HEADER) Integer userId) {
        return ResponseEntity.ok(bookingService.findByOwner(state, userId));
    }
}
