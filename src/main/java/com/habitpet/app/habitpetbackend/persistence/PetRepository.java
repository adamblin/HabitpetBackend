package com.habitpet.app.habitpetbackend.persistence;

import com.habitpet.app.habitpetbackend.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, String> {
}
