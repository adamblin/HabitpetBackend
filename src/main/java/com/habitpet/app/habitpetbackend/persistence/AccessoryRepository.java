package com.habitpet.app.habitpetbackend.persistence;

import com.habitpet.app.habitpetbackend.domain.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, String> {
}
