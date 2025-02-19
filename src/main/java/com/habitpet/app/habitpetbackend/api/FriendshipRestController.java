package com.habitpet.app.habitpetbackend.api;

import com.habitpet.app.habitpetbackend.application.FriendshipService;
import com.habitpet.app.habitpetbackend.application.dto.FriendshipDTO;
import com.habitpet.app.habitpetbackend.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendships")
public class FriendshipRestController {
    private final FriendshipService friendshipService;

    public FriendshipRestController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }


    @PostMapping("/request/{usernameId}")
    public ResponseEntity<Void> sendFriendRequest(@AuthenticationPrincipal User user, @PathVariable String usernameId) {
        friendshipService.sendFriendRequest(user, usernameId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/accept/{senderId}")
    public ResponseEntity<Void> acceptFriendRequest(@AuthenticationPrincipal User user, @PathVariable String senderId) {
        friendshipService.acceptFriendRequest(user, senderId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/requests")
    public ResponseEntity<List<FriendshipDTO>> getFriendRequests(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(friendshipService.getPendingFriendRequests(user));
    }


    @GetMapping()
    public ResponseEntity<List<FriendshipDTO>> getAllFriends(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(friendshipService.getAllFriends(user));
    }
}
