package com.habitpet.app.habitpetbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.habitpet.app.habitpetbackend.application.dto.UserRegisterDTO;
import com.habitpet.app.habitpetbackend.domain.enums.CurrencyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private String id = UUID.randomUUID().toString();

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email no puede estar vacío")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    @Column(unique = true)
    private String username;

    @JsonIgnore
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private int habitCoins = 0;
    private int habitGems = 0;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pet_id", unique = true)
    @JsonManagedReference
    private Pet pet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> friendships;

    public User() {}

    public User(UserRegisterDTO dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.habitCoins = 0;
        this.habitGems = 0;
    }

    public void earnCurrency(CurrencyType currency, int amount) {
        if (amount <= 0) return;

        switch (currency) {
            case HABIT_COINS -> habitCoins += amount;
            case HABIT_GEMS  -> habitGems += amount;
        }
    }

    public boolean spendCurrency(CurrencyType currency, int amount) {
        if (amount <= 0) return false;

        return switch (currency) {
            case HABIT_COINS -> {
                if (habitCoins >= amount) {
                    habitCoins -= amount;
                    yield true;
                } else yield false;
            }
            case HABIT_GEMS -> {
                if (habitGems >= amount) {
                    habitGems -= amount;
                    yield true;
                } else yield false;
            }
        };
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getHabitCoins() { return habitCoins; }
    public void setHabitCoins(int coins) { this.habitCoins = coins; }

    public int getHabitGems() { return habitGems; }
    public void setHabitGems(int habitGems) { this.habitGems = habitGems; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public List<Friendship> getFriendships() { return friendships; }
    public void setFriendships(List<Friendship> friendships) { this.friendships = friendships; }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() { return true; }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() { return true; }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @JsonIgnore
    @Override
    public boolean isEnabled() { return true; }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
