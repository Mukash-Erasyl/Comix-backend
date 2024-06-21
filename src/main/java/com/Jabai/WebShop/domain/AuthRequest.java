package com.Jabai.WebShop.domain;


public class AuthRequest {
    private String username;
    private String password;

    // Конструктор по умолчанию
    public AuthRequest() {
    }

    // Конструктор с параметрами
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттер для username
    public String getUsername() {
        return username;
    }

    // Сеттер для username
    public void setUsername(String username) {
        this.username = username;
    }

    // Геттер для password
    public String getPassword() {
        return password;
    }

    // Сеттер для password
    public void setPassword(String password) {
        this.password = password;
    }
}
