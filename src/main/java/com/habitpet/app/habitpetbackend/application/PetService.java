package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.PetStateDTO;
import com.habitpet.app.habitpetbackend.domain.Pet;
import com.habitpet.app.habitpetbackend.persistence.PetRepository;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
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

    private void degradePetStats(Pet pet) {
        LocalDateTime now = LocalDateTime.now();
        Duration elapsed = Duration.between(pet.getLastUpdated(), now);
        long hoursPassed = elapsed.toHours();

        if (hoursPassed <= 0) return;

        int degradePerHour = 5;

        int newSatiated = Math.max(0, pet.getSatiated() - (int)(hoursPassed * degradePerHour));
        int newCleanliness = Math.max(0, pet.getCleanliness() - (int)(hoursPassed * degradePerHour));
        int newHapyness = Math.max(0, pet.getHapyness() - (int)(hoursPassed * degradePerHour));

        pet.setSatiated(newSatiated);
        pet.setCleanliness(newCleanliness);
        pet.setHapyness(newHapyness);
        pet.setLastUpdated(now);
    }

}



