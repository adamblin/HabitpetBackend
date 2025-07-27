package com.habitpet.app.habitpetbackend.api;

import com.habitpet.app.habitpetbackend.application.dto.CurrencyResponseDTO;
import com.habitpet.app.habitpetbackend.application.dto.EarnCurrencyDTO;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserCurrencyRestController {
    private final UserRepository userRepository;

    public UserCurrencyRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Obtener las monedas del usuario
    @GetMapping("/{username}/currency")
    public ResponseEntity<?> getUserCurrency(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok(
                        new CurrencyResponseDTO(user.getHabitCoins(), user.getHabitGems())))
                .orElse(ResponseEntity.badRequest().build());
    }

    // Sumar monedas al usuario
    @PostMapping("/{username}/earn")
    public ResponseEntity<?> earnCurrency(@PathVariable String username, @RequestBody EarnCurrencyDTO request) {
        return userRepository.findByUsername(username).map(user -> {
            user.earnCurrency(request.currency(), request.amount());
            userRepository.save(user);
            return ResponseEntity.ok(new CurrencyResponseDTO(user.getHabitCoins(), user.getHabitGems()));
        }).orElse(ResponseEntity.badRequest().build());
    }
}
