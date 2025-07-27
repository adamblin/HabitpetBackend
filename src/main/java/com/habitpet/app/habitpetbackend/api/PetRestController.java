package com.habitpet.app.habitpetbackend.api;

import com.habitpet.app.habitpetbackend.application.PetService;
import com.habitpet.app.habitpetbackend.application.dto.PetStateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/pets")
public class PetRestController {

    private final PetService petService;


    public PetRestController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/me/state")
    public ResponseEntity<PetStateDTO> getMyPetState(Authentication auth) {
        return ResponseEntity.ok(petService.getPetStateByUsername(auth.getName()));
    }

    @PutMapping("/me/state")
    public ResponseEntity<Void> updateMyPetState(@RequestBody PetStateDTO dto, Authentication auth) {
        petService.updatePetStateByUsername(auth.getName(), dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me/state")
    public ResponseEntity<Void> resetMyPetState(Authentication auth) {
        petService.resetPetStateByUsername(auth.getName());
        return ResponseEntity.noContent().build();
    }


}