package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.PetDTO;
import com.habitpet.app.habitpetbackend.application.dto.UserDTO;
import com.habitpet.app.habitpetbackend.domain.Pet;
import com.habitpet.app.habitpetbackend.domain.User;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import com.habitpet.app.habitpetbackend.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }


    public String login(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> {
                    System.out.println("Login failed: email not found.");
                    return new IllegalArgumentException("Invalid email or password.");
                });

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            System.out.println("Login failed: incorrect password.");
            throw new IllegalArgumentException("Invalid email or password.");
        }

        System.out.println("Login successful: generating token.");

        // Usa solo el email para generar el token
        return jwtTokenProvider.generateToken(user.getEmail());
    }


    @Transactional
    public Pet createPetForUser(PetDTO petDTO, User user) {
        if (user.getPet() != null) {
            throw new IllegalArgumentException("El usuario ya tiene una mascota asignada.");
        }
        Pet pet = new Pet(petDTO);
        pet.setUser(user);
        user.setPet(pet);
        userRepository.save(user);
        return pet;
    }

    public Pet getPetForUser(User user) {
        Pet pet = user.getPet();
        if (pet == null) {
            throw new IllegalArgumentException("El usuario no tiene una mascota.");
        }
        return pet;
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
