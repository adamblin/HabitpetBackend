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

    Optional<Friendship> findByIdAndFriend(String requestId, User friend);

    boolean existsByUserAndFriend(User user, User friend);

    @Query("SELECT new com.habitpet.app.habitpetbackend.application.dto.FriendshipDTO(u.username, uf.username, f.accepted) " +
            "FROM com.habitpet.app.habitpetbackend.domain.Friendship f " +
            "JOIN f.user u " +
            "JOIN f.friend uf " +
            "WHERE (u.id = :userId OR uf.id = :userId) AND f.accepted = true")
    List<FriendshipDTO> findAcceptedFriendshipsWithUsernames(@Param("userId") String userId);




}
