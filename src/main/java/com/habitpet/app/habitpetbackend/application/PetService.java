package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.PetStateDTO;
import com.habitpet.app.habitpetbackend.domain.InteractionType;
import com.habitpet.app.habitpetbackend.domain.Pet;
import com.habitpet.app.habitpetbackend.domain.enums.ActionType;
import com.habitpet.app.habitpetbackend.persistence.InteractionTypeRepository;
import com.habitpet.app.habitpetbackend.persistence.PetRepository;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final InteractionTypeRepository interactionTypeRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository, InteractionTypeRepository interactionTypeRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.interactionTypeRepository = interactionTypeRepository;
    }

    public PetStateDTO getPetStateByUsername(String username) {
        Pet pet = userRepository.findByUsername(username)
                .orElseThrow()
                .getPet();

        degradePetStats(pet);
        petRepository.save(pet);

        return new PetStateDTO(pet.getSatiated(), pet.getCleanliness(), pet.getHapyness());
    }

    public void updatePetStateByUsername(String username, PetStateDTO dto) {
        Pet pet = userRepository.findByUsername(username)
                .orElseThrow()
                .getPet();

        pet.setSatiated(dto.getSatiated());
        pet.setCleanliness(dto.getCleanliness());
        pet.setHapyness(dto.getHapyness());
        petRepository.save(pet);
    }

    public void resetPetStateByUsername(String username) {
        Pet pet = userRepository.findByUsername(username)
                .orElseThrow()
                .getPet();

        pet.setSatiated(0);
        pet.setCleanliness(0);
        pet.setHapyness(0);
        petRepository.save(pet);
    }

    public void interactWithPet(String username, String action, String type) {
        Pet pet = userRepository.findByUsername(username)
                .orElseThrow()
                .getPet();

        degradePetStats(pet);

        InteractionType interaction = interactionTypeRepository
                .findByActionAndType(ActionType.fromString(action), type.toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Interacción no válida"));


        pet.setSatiated(limit(pet.getSatiated() + interaction.getDeltaSatiated()));
        pet.setCleanliness(limit(pet.getCleanliness() + interaction.getDeltaCleanliness()));
        pet.setHapyness(limit(pet.getHapyness() + interaction.getDeltaHapyness()));
        pet.setLastUpdated(LocalDateTime.now());

        petRepository.save(pet);
    }

    private void degradePetStats(Pet pet) {
        LocalDateTime now = LocalDateTime.now();
        Duration elapsed = Duration.between(pet.getLastUpdated(), now);
        long hoursPassed = elapsed.toHours();

        if (hoursPassed <= 0) return;

        int degradePerHour = 5;

        pet.setSatiated(Math.max(0, pet.getSatiated() - (int)(hoursPassed * degradePerHour)));
        pet.setCleanliness(Math.max(0, pet.getCleanliness() - (int)(hoursPassed * degradePerHour)));
        pet.setHapyness(Math.max(0, pet.getHapyness() - (int)(hoursPassed * degradePerHour)));
        pet.setLastUpdated(now);
    }

    private int limit(int value) {
        return Math.max(0, Math.min(100, value));
    }
}



