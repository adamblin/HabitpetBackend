package com.habitpet.app.habitpetbackend.api;

import com.habitpet.app.habitpetbackend.application.AccessoryShopService;
import com.habitpet.app.habitpetbackend.application.dto.AccessoryDTO;
import com.habitpet.app.habitpetbackend.application.dto.PurchaseAccessoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopAccessoryController {

    private final AccessoryShopService accessoryShopService;

    public ShopAccessoryController(AccessoryShopService accessoryShopService) {
        this.accessoryShopService = accessoryShopService;
    }
    @GetMapping("/accessories")
    public List<AccessoryDTO> getAllAccessories() {
        return accessoryShopService.getAllAccessories();
    }
    @PostMapping("/buy-accessory")
    public ResponseEntity<?> buyAccessory(@RequestBody PurchaseAccessoryDTO request) {
        return accessoryShopService.buyAccessory(request.username(), request.accessoryId());
    }
}
