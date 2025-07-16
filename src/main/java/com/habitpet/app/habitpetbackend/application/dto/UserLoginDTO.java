package com.habitpet.app.habitpetbackend.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {
    @Size(min = 3, max = 20, message = "El usuario debe tener entre 3 y 20 caracteres")
    @NotBlank(message = "El email no puede estar vacío")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
