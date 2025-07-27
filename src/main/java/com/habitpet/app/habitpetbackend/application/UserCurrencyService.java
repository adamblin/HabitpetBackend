package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.CurrencyResponseDTO;
import com.habitpet.app.habitpetbackend.application.dto.EarnCurrencyDTO;
import com.habitpet.app.habitpetbackend.domain.User;
import com.habitpet.app.habitpetbackend.domain.enums.CurrencyType;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCurrencyService {

    private final UserRepository userRepository;

    public UserCurrencyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getCurrency(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");

        User user = userOpt.get();
        return ResponseEntity.ok(new CurrencyResponseDTO(user.getHabitCoins(), user.getHabitGems()));
    }

    public ResponseEntity<?> earnCurrency(String username, EarnCurrencyDTO dto) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");

        User user = userOpt.get();
        user.earnCurrency(dto.currency(), dto.amount());
        userRepository.save(user);

        return ResponseEntity.ok(new CurrencyResponseDTO(user.getHabitCoins(), user.getHabitGems()));
    }
}
