package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByItem_Owner_Id(Integer userId);

    List<Comment> findByItem_Id(Integer itemId);
}
