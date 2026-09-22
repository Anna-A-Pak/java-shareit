package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestWithItemsDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto create(ItemRequestDto dto, Integer userId);

    List<RequestWithItemsDto> findAllByRequester(Integer userId);

    List<ItemRequestDto> findAllRequests(Integer userId);

    RequestWithItemsDto findById(Integer id);
}
