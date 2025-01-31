package com.habitpet.app.habitpetbackend.persistence;

import com.habitpet.app.habitpetbackend.domain.Friendship;
import com.habitpet.app.habitpetbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {

    Optional<Friendship> findByIdAndFriend(String requestId, User friend);

    boolean existsByUserAndFriend(User user, User friend);

    List<Friendship> findByUserOrFriendAndAcceptedTrue(User user1, User user2);

}
