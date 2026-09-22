package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestWithItemsDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository requestRepository;
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto create(ItemRequestDto dto, Integer userId) {
        User user = UserMapper.mapToUser(userService.findById(userId));
        ItemRequest itemRequest = ItemRequestMapper.mapToItemRequest(dto, user);
        itemRequest.setCreated(LocalDateTime.now());
        return ItemRequestMapper.mapToDto(requestRepository.save(itemRequest));

    }

    @Override
    public List<RequestWithItemsDto> findAllByRequester(Integer userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created");
        List<RequestWithItems> requests = requestRepository.findAllByRequesterId(userId, sort);

        List<Integer> requestIds = requests
                .stream()
                .map(RequestWithItems::getId)
                .toList();

        List<Item> items = itemRepository.findAllByRequestIdIn(requestIds);

        Map<Integer, List<Item>> itemsMap = items
                .stream()
                .collect(Collectors.groupingBy(item -> item.getRequest().getId()));

        for (RequestWithItems request : requests) {
            if (itemsMap.containsKey(request.getId())) {
                request.setItems(itemsMap.get(request.getId()));
            }
        }

        return ItemRequestMapper.mapToListRequestWithItemsDto(requests);
    }

    @Override
    public List<ItemRequestDto> findAllRequests(Integer userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created");
        List<ItemRequest> itemRequests = requestRepository.findAllByRequesterIdIsNot(userId, sort);

        return ItemRequestMapper.mapToListDto(itemRequests);
    }

    @Override
    public RequestWithItemsDto findById(Integer id) {
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
        ItemRequest itemRequest = getItemRequest(id);
        RequestWithItems request = ItemRequestMapper.mapToRequestWithItems(itemRequest);
        List<Item> items = itemRepository.findAllByRequestId(request.getId());
        request.setItems(items);
        return ItemRequestMapper.matToRequestWithItemsDto(request);
    }

    private ItemRequest getItemRequest(Integer id) {
        Optional<ItemRequest> requestOptional = requestRepository.findById(id);
        if (requestOptional.isEmpty()) {
            throw new NotFoundException("Запрос с id = " + id + " не найден");
        }
        return requestOptional.get();
    }
}
