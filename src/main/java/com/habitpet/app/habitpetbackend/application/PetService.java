package com.habitpet.app.habitpetbackend.application;

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


}
