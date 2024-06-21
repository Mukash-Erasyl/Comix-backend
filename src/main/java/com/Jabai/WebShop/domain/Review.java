package com.Jabai.WebShop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating; // Рейтинг отзыва
    private String comment; // Комментарий отзыва
    private LocalDateTime createdAt; // Дата и время создания отзыва
    private boolean approved; // Флаг подтверждения отзыва администратором

    @ManyToOne
    @JoinColumn(name = "user_id") // Предполагается, что у пользователя есть поле id
    private User user; // Пользователь, оставивший отзыв

    public Review() {
        // Пустой конструктор
    }

    public Review(int rating, String comment, LocalDateTime createdAt, boolean approved, User user) {
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.approved = approved;
        this.user = user;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
