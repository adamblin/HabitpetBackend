package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.PetStateDTO;
import com.habitpet.app.habitpetbackend.domain.Pet;
import com.habitpet.app.habitpetbackend.persistence.PetRepository;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;

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
}



