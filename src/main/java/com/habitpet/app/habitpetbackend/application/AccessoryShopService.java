package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.AccessoryDTO;
import com.habitpet.app.habitpetbackend.domain.Accessory;
import com.habitpet.app.habitpetbackend.domain.Pet;
import com.habitpet.app.habitpetbackend.domain.User;
import com.habitpet.app.habitpetbackend.domain.enums.CurrencyType;
import com.habitpet.app.habitpetbackend.persistence.AccessoryRepository;
import com.habitpet.app.habitpetbackend.persistence.PetRepository;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccessoryShopService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final AccessoryRepository accessoryRepository;

    public AccessoryShopService(UserRepository userRepository, PetRepository petRepository, AccessoryRepository accessoryRepository) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.accessoryRepository = accessoryRepository;
    }

    public List<AccessoryDTO> getAllAccessories() {
        return accessoryRepository.findAll().stream()
                .map(AccessoryDTO::fromEntity)
                .toList();
    }

    public ResponseEntity<?> buyAccessory(String username, String accessoryId) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");

        User user = userOpt.get();
        Pet pet = user.getPet();
        if (pet == null) return ResponseEntity.badRequest().body("El usuario no tiene mascota");

        Optional<Accessory> accOpt = accessoryRepository.findById(accessoryId);
        if (accOpt.isEmpty()) return ResponseEntity.badRequest().body("Accesorio no encontrado");

        Accessory accessory = accOpt.get();

        if (pet.getAccessories().contains(accessory)) {
            return ResponseEntity.badRequest().body("La mascota ya tiene este accesorio");
        }

        // Verificar si tiene suficientes monedas y gemas
        if (user.getHabitCoins() < accessory.getHabitCoinsCost()) {
            return ResponseEntity.badRequest().body("No tienes suficientes habitCoins");
        }

        if (user.getHabitGems() < accessory.getHabitGemsCost()) {
            return ResponseEntity.badRequest().body("No tienes suficientes habitGems");
        }

        // Gastar ambas monedas
        user.spendCurrency(CurrencyType.HABIT_COINS, accessory.getHabitCoinsCost());
        user.spendCurrency(CurrencyType.HABIT_GEMS, accessory.getHabitGemsCost());

        // Agregar accesorio a la mascota
        pet.getAccessories().add(accessory);
        userRepository.save(user);
        petRepository.save(pet);

        return ResponseEntity.ok(Map.of(
                "message", "Compra realizada con éxito",
                "habitCoins", user.getHabitCoins(),
                "habitGems", user.getHabitGems()
        ));
    }


}
