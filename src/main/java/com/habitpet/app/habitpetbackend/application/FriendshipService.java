package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.FriendshipDTO;
import com.habitpet.app.habitpetbackend.domain.Friendship;
import com.habitpet.app.habitpetbackend.domain.User;
import com.habitpet.app.habitpetbackend.persistence.FriendshipRepository;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendshipService {
    private final UserRepository userRepository;

    private final FriendshipRepository friendshipRepository;

    public FriendshipService(UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }
    @Transactional
    public void sendFriendRequest(User user, String usernameId) {
        User friend = userRepository.findByUsername(usernameId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (friendshipRepository.existsByUserAndFriend(user, friend)) {
            throw new RuntimeException("Friend request already sent");
        }

        Friendship request = new Friendship(user, friend, false);
        friendshipRepository.save(request);
    }

    @Transactional
    public void acceptFriendRequest(User friend, String requestId) {
        Friendship request = friendshipRepository.findByIdAndFriend(requestId, friend)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        request.setAccepted(true);
        friendshipRepository.save(request);
    }

    @Transactional(readOnly = true)
    public List<FriendshipDTO> getFriends(User user) {
        return friendshipRepository.findByUserOrFriendAndAcceptedTrue(user, user).stream()
                .map(FriendshipDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
