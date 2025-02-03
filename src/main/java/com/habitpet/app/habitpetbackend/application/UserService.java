package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.PetDTO;
import com.habitpet.app.habitpetbackend.application.dto.UserDTO;
import com.habitpet.app.habitpetbackend.domain.Pet;
import com.habitpet.app.habitpetbackend.domain.User;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import com.habitpet.app.habitpetbackend.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
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

    public String register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);

        // Generar y devolver el token
        return jwtTokenProvider.generateToken(user.getEmail());
    }

    public String login(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

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

    public ResponseEntity<Pet> getPetForUser(User user) {
        User fullUser = findById(user.getId()); // ✅ Llamamos directamente al método findById de esta clase
        Pet pet = fullUser.getPet();

        if (pet == null) {
            return ResponseEntity.noContent().build(); // Devuelve 204 en vez de lanzar una excepción
        }

        return ResponseEntity.ok(pet);
    }




    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
