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

    @PostMapping
    public ResponseEntity<AccessoryDTO> createAccessory(@RequestBody AccessoryDTO dto) {
        Accessory accessory = accessoryService.createAccessory(dto);
        return ResponseEntity.ok(AccessoryDTO.fromEntity(accessory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessoryDTO> getAccessoryById(@PathVariable String id) {
        Optional<Accessory> accessoryOpt = accessoryService.getAccessoryById(id);
        return accessoryOpt
                .map(accessory -> ResponseEntity.ok(AccessoryDTO.fromEntity(accessory)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessoryDTO> updateAccessory(@PathVariable String id, @RequestBody AccessoryDTO dto) {
        Accessory updated = accessoryService.updateAccessory(id, dto);
        return ResponseEntity.ok(AccessoryDTO.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessory(@PathVariable String id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AccessoryDTO>> getAllAccessories() {
        return ResponseEntity.ok(accessoryService.getAllAccessories());
    }
}
