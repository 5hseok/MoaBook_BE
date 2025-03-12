package com.server.moabook.group.domain;

import com.server.moabook.book.domain.Book;
import com.server.moabook.oauth2.entity.SocialUserEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import java.util.List;

@Entity
@Table(name = "book_groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    private String name;

    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SocialUserEntity user;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    public void changeName(String newName) {
        this.name = newName;
    }
    public void changeColor(String newColor) {this.color = newColor;}

}
