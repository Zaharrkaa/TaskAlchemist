package ru.taskalchemist.supportWebapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 30, min = 3, message = "Имя дложно быть не короче 3 и не длиннее 30 символов")
    @NotEmpty(message = "Это поле нужно заполнить")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Это поле нужно заполнить")
    @Size(min = 6, message = "пароль не может быть короче 6 символов")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Это поле нужно заполнить")
    @Column(name = "email")
    @Email(message = "Неправильный email")
    private String email;

    @Column(name = "role")
    private String role;

    public User() {

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}