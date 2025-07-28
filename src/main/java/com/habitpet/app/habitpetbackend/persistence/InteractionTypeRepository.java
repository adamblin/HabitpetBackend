package com.habitpet.app.habitpetbackend.persistence;

import com.habitpet.app.habitpetbackend.domain.InteractionType;
import com.habitpet.app.habitpetbackend.domain.enums.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InteractionTypeRepository extends JpaRepository<InteractionType, UUID> {
    Optional<InteractionType> findByActionAndType(ActionType action, String type);

}