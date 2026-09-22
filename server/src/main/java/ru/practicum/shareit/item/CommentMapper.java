package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.user.User;

import java.util.List;

@UtilityClass
public class CommentMapper {
    public static CommentDto mapToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getItem().getId(),
                comment.getAuthor().getName(),
                comment.getCreated()
        );
    }

    public static Comment mapToComment(CommentDto commentDto, Item  item, User user) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(commentDto.getCreated());
        return comment;
    }

    public static List<CommentDto> mapToListCommentDto(List<Comment> comments) {
        return comments
                .stream()
                .map(CommentMapper::mapToDto)
                .toList();
    }
}
