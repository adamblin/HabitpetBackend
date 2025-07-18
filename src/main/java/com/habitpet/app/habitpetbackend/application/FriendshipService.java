package com.habitpet.app.habitpetbackend.application;

import com.habitpet.app.habitpetbackend.application.dto.FriendshipDTO;
import com.habitpet.app.habitpetbackend.domain.Friendship;
import com.habitpet.app.habitpetbackend.domain.User;
import com.habitpet.app.habitpetbackend.domain.enums.FriendshipStatus;
import com.habitpet.app.habitpetbackend.persistence.FriendshipRepository;
import com.habitpet.app.habitpetbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendshipService {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public FriendshipService(UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    // Enviar solicitud de amistad
    @Transactional
    public void sendFriendRequest(User user, String usernameId) {
        User friend = userRepository.findByUsername(usernameId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (friendshipRepository.existsByUserAndFriend(user, friend)) {
            throw new RuntimeException("Friend request already sent");
        }

        Friendship request = new Friendship(user, friend, FriendshipStatus.PENDING);
        friendshipRepository.save(request);
    }

    // Aceptar solicitud de amistad
    @Transactional
    public void acceptFriendRequest(User currentUser, String senderUsername) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender user not found"));

        Friendship request = friendshipRepository.findPendingRequest(sender.getId(), currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        request.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepository.save(request);
    }


    // Obtener todas las solicitudes pendientes
    @Transactional(readOnly = true)
    public List<FriendshipDTO> getPendingFriendRequests(User user) {
        return friendshipRepository.findPendingRequests(user.getId()).stream()
                .map(f -> new FriendshipDTO(f.getUser().getUsername(), f.getFriend().getUsername(), FriendshipStatus.PENDING))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FriendshipDTO> getAllFriends(User user) {
        return friendshipRepository.findAcceptedFriendUsernamesOnly(user.getId());
    }

}
