package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestWithItemsDto;
import ru.practicum.shareit.user.User;

import java.util.List;

public class ItemRequestMapper {
    public static ItemRequestDto mapToDto(ItemRequest itemRequest) {
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getCreated()
        );
    }

    public static ItemRequest mapToItemRequest(ItemRequestDto itemRequestDto, User user) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemRequestDto.getId());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setRequester(user);

        return itemRequest;
    }

    public static List<ItemRequestDto> mapToListDto(List<ItemRequest> itemRequests) {
        return itemRequests
                .stream()
                .map(ItemRequestMapper::mapToDto)
                .toList();
    }

    public static RequestWithItemsDto matToRequestWithItemsDto(RequestWithItems requestWithItems) {
        RequestWithItemsDto dto = new RequestWithItemsDto();
        dto.setId(requestWithItems.getId());
        dto.setDescription(requestWithItems.getDescription());
        dto.setCreated(requestWithItems.getCreated());
        dto.setItems(requestWithItems.getItems());
        return dto;
    }

    public static List<RequestWithItemsDto> mapToListRequestWithItemsDto(List<RequestWithItems> requestWithItems) {
        return requestWithItems
                .stream()
                .map(ItemRequestMapper::matToRequestWithItemsDto)
                .toList();
    }

    public static RequestWithItems mapToRequestWithItems(ItemRequest itemRequest) {
        return new RequestWithItems(
                itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getCreated()
        );
    }
}
