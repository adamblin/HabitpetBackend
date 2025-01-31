package com.habitpet.app.habitpetbackend.api;

import com.habitpet.app.habitpetbackend.application.PetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pets")
public class PetRestController {

    private final PetService petService;


    public PetRestController(PetService petService) {
        this.petService = petService;
    }



}