package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.ItemBookingDate;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByBooker_IdAndEndIsBefore(
            Integer bookerId,
            LocalDateTime end,
            Sort sort
    );

    List<Booking> findByBooker_IdAndStartIsBeforeAndEndIsAfter(
            Integer bookerId,
            LocalDateTime start,
            LocalDateTime end,
            Sort sort
    );

    List<Booking> findByBooker_IdAndStartIsAfter(
            Integer bookerId,
            LocalDateTime start,
            Sort sort
    );

    List<Booking> findByBooker_IdAndStatus(
            Integer bookerId,
            BookingState bookingState,
            Sort sort
    );

    List<Booking> findByBooker_Id(Integer bookerId, Sort sort);

    List<Booking> findByItem_Owner_IdAndEndIsBefore(
            Integer ownerId,
            LocalDateTime end,
            Sort sort
    );

    List<Booking> findByItem_Owner_IdAndStartIsBeforeAndEndIsAfter(
            Integer ownerId,
            LocalDateTime start,
            LocalDateTime end,
            Sort sort
    );

    List<Booking> findByItem_Owner_IdAndStartIsAfter(
            Integer ownerId,
            LocalDateTime start,
            Sort sort
    );

    List<Booking> findByItem_Owner_IdAndStatus(
            Integer ownerId,
            BookingState bookingState,
            Sort sort
    );

    List<Booking> findByItem_Owner_Id(Integer ownerId, Sort sort);

    @Query("select new ru.practicum.shareit.item.ItemBookingDate(" +
            "b.item.id, " +
            "max(case when b.start < ?2 then b.start end), " +
            "min(case when b.start > ?2 then b.start end)) " +
            "from Booking b " +
            "where b.item.owner.id = ?1 " +
            "group by b.item.id")
    List<ItemBookingDate> getBookingDate(Integer ownerId, LocalDateTime time);

    Booking findByItem_Id(Integer itemId);
}
