package ru.practicum.shareit.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
	private final BookingClient bookingClient;
	public static final String USER_HEADER = "X-Sharer-User-Id";

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody BookItemRequestDto dto,
										 @RequestHeader(USER_HEADER) Integer userId) {
		return bookingClient.create(userId, dto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> prove(@PathVariable Integer id,
										@RequestParam Boolean approved,
										@RequestHeader(USER_HEADER) Integer userId) {
		return bookingClient.prove(id, approved, userId);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getBooking(@PathVariable Integer id,
											 @RequestHeader(USER_HEADER) Integer userId) {
		return bookingClient.getBooking(id, userId);
	}

	@GetMapping
	public ResponseEntity<Object> findByBooker(@RequestParam(defaultValue = "ALL") String state,
											   @RequestHeader(USER_HEADER) Integer userId) {
		return bookingClient.findByBooker(state, userId);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> findByOwner(@RequestParam(defaultValue = "ALL") String state,
											  @RequestHeader(USER_HEADER) Integer userId) {
		return bookingClient.findByBooker(state, userId);
	}
}
