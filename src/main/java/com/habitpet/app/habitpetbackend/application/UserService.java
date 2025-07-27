package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.PetDTO;
import com.habitpet.app.habitpetbackend.application.dto.UserRegisterDTO;
import com.habitpet.app.habitpetbackend.application.dto.UserLoginDTO;
import com.habitpet.app.habitpetbackend.application.dto.UserResponseDTO;
import com.habitpet.app.habitpetbackend.domain.Pet;
import com.habitpet.app.habitpetbackend.domain.User;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import com.habitpet.app.habitpetbackend.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public Map<String, Object> register(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new IllegalArgumentException("User with this username already exists.");
        }

        User user = new User(userRegisterDTO);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return Map.of(
                "token", token,
                "user", new UserResponseDTO(user)
        );
    }

    public Map<String, Object> login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return Map.of(
                "token", token,
                "user", new UserResponseDTO(user)
        );
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
        User fullUser = findById(user.getId()); // Llamamos directamente al método findById de esta clase
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
