package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.AccessoryDTO;
import com.habitpet.app.habitpetbackend.domain.Accessory;
import com.habitpet.app.habitpetbackend.persistence.AccessoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    public Accessory createAccessory(AccessoryDTO accessoryDTO) {
        Accessory accessory = new Accessory(accessoryDTO);
        return accessoryRepository.save(accessory);
    }

    public Optional<Accessory> getAccessoryById(String id) {
        return accessoryRepository.findById(id);
    }

    public Accessory updateAccessory(String id, AccessoryDTO accessoryDTO) {
        return accessoryRepository.findById(id).map(accessory -> {
            accessory.setName(accessoryDTO.getName());
            accessory.setCoins(accessoryDTO.getCoins());
            return accessoryRepository.save(accessory);
        }).orElseThrow(() -> new RuntimeException("Accessory not found with ID: " + id));
    }

    public void deleteAccessory(String id) {
        if (!accessoryRepository.existsById(id)) {
            throw new RuntimeException("Accessory not found with ID: " + id);
        }
        accessoryRepository.deleteById(id);
    }

    public List<AccessoryDTO> getAllAccessories() {
        List<Accessory> accessories = accessoryRepository.findAll();
        return accessories.stream()
                .map(AccessoryDTO::new)
                .collect(Collectors.toList());
    }
}
