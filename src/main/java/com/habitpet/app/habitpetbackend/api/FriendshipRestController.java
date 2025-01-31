package com.habitpet.app.habitpetbackend.api;

import com.habitpet.app.habitpetbackend.application.FriendshipService;
import com.habitpet.app.habitpetbackend.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<Void> acceptFriendRequest(@AuthenticationPrincipal User user, @PathVariable String requestId) {
        friendshipService.acceptFriendRequest(user, requestId);
        return ResponseEntity.ok().build();
    }



}
