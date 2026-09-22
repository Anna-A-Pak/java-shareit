package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Integer> {

    List<RequestWithItems> findAllByRequesterId(Integer userId, Sort sort);

    List<ItemRequest> findAllByRequesterIdIsNot(Integer userId, Sort sort);
}
