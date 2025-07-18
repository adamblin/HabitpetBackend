package com.habitpet.app.habitpetbackend.persistence;

import com.habitpet.app.habitpetbackend.application.dto.FriendshipDTO;
import com.habitpet.app.habitpetbackend.domain.Friendship;
import com.habitpet.app.habitpetbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {

    // Verifica si una amistad ya existe
    boolean existsByUserAndFriend(User user, User friend);

    // Buscar una solicitud pendiente
    @Query("SELECT f FROM Friendship f WHERE f.user.id = :senderId AND f.friend.id = :receiverId AND f.status = 'PENDING'")
    Optional<Friendship> findPendingRequest(@Param("senderId") String senderId, @Param("receiverId") String receiverId);

    // Obtener todas las solicitudes pendientes
    @Query("SELECT f FROM Friendship f WHERE f.friend.id = :userId AND f.status = 'PENDING'")
    List<Friendship> findPendingRequests(@Param("userId") String userId);

    @Query("""
    SELECT new com.habitpet.app.habitpetbackend.application.dto.FriendshipDTO(
        CASE 
            WHEN f.user.id = :userId THEN f.friend.username
            ELSE f.user.username
        END,
        com.habitpet.app.habitpetbackend.domain.enums.FriendshipStatus.ACCEPTED)
    FROM Friendship f
    WHERE (f.user.id = :userId OR f.friend.id = :userId)
    AND f.status = 'ACCEPTED'
""")
    List<FriendshipDTO> findAcceptedFriendUsernamesOnly(@Param("userId") String userId);






}
