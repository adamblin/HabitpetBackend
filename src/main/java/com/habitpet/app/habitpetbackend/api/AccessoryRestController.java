package com.habitpet.app.habitpetbackend.api;

import com.habitpet.app.habitpetbackend.application.AccessoryService;
import com.habitpet.app.habitpetbackend.application.dto.AccessoryDTO;
import com.habitpet.app.habitpetbackend.domain.Accessory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accessories")
public class AccessoryRestController {

    private final AccessoryService accessoryService;

    public AccessoryRestController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    @PostMapping()
    public ResponseEntity<Accessory> createAccessory(@RequestBody AccessoryDTO accesoryDTO){
        Accessory accessory = accessoryService.createAccessory(accesoryDTO);
        return ResponseEntity.ok(accessory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accessory> getAccessoryById(@PathVariable String id) {
        return accessoryService.getAccessoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accessory> updateAccessory(@PathVariable String id, @RequestBody AccessoryDTO accessoryDTO) {
        return ResponseEntity.ok(accessoryService.updateAccessory(id, accessoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessory(@PathVariable String id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }

}