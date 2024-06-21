package com.Jabai.WebShop.domain;

public class AuthResponse {
    private String jwt;

    // Конструктор по умолчанию
    public AuthResponse() {
    }

    // Конструктор с параметром jwt
    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    // Геттер для jwt
    public String getJwt() {
        return jwt;
    }

    // Сеттер для jwt
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
