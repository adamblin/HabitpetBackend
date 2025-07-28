package com.habitpet.app.habitpetbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.habitpet.app.habitpetbackend.application.dto.PetDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;
    private int satiated; // De 0 (no té gana) a 100 (molt famolenca)
    private int cleanliness; // De 0 (bruta) a 100 (molt neta)
    private int hapyness; // De 0 (infleiç) a 100 (molt feliç)
    private LocalDateTime lastUpdated;

    @ManyToMany
    @JoinTable(
            name = "pet_accessories",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "accessory_id")
    )
    @JsonManagedReference
    private List<Accessory> accessories = new ArrayList<>();
    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference // Indica el costat invers de la relació
    private User user;

    public Pet() {
    }

    public Pet(PetDTO dto) {
        this.name = dto.getName();
        this.satiated = 50;
        this.cleanliness = 50;
        this.hapyness = 50;
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getSatiated() { return satiated; }
    public void setSatiated(int hungryness) { this.satiated = hungryness; }
    public int getCleanliness() { return cleanliness; }
    public void setCleanliness(int cleanliness) { this.cleanliness = cleanliness; }
    public List<Accessory> getAccessories() { return accessories; }
    public void setAccessories(List<Accessory> accessories) { this.accessories = accessories; }
    public int getHapyness() {
        return hapyness;
    }

    public void setHapyness(int hapyness) {
        this.hapyness = hapyness;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
