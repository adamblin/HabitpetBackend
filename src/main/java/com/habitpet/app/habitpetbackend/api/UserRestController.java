package com.habitpet.app.habitpetbackend.api;

import com.habitpet.app.habitpetbackend.application.UserService;
import com.habitpet.app.habitpetbackend.application.dto.PetDTO;
import com.habitpet.app.habitpetbackend.domain.Pet;
import com.habitpet.app.habitpetbackend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Pet> createPet(@RequestBody PetDTO petDTO, @AuthenticationPrincipal User user) {
        // Carrega el User complet
        User fullUser = userService.findById(user.getId());
        Pet pet = userService.createPetForUser(petDTO, fullUser);
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/pet")
    public ResponseEntity<Pet> getPet(@AuthenticationPrincipal User user) {
        // Carrega el User complet
        User fullUser = userService.findById(user.getId());
        Pet pet = userService.getPetForUser(fullUser);
        return ResponseEntity.ok(pet);
    }


}