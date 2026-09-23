package ru.practicum.shareit.item;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;


@Getter
@Setter
@Entity
@Table(name = "items")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Item {
    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    private String name;
    private String description;
    private Boolean available;
    @ManyToOne
    @JoinColumn(name = "id_request")
    private ItemRequest request;

    public Item(Integer id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Item(Integer id, User owner, String name, String description, Boolean available, ItemRequest request) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.available = available;
        this.request = request;
    }

}
