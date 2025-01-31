package com.habitpet.app.habitpetbackend.application.dto;

import com.habitpet.app.habitpetbackend.domain.Friendship;

public class FriendshipDTO {

    private String userId;
    private String friendId;

    private boolean accepted;

    public FriendshipDTO() {}

    public FriendshipDTO(Friendship friendship) {
        this.userId = friendship.getUser().getId();
        this.friendId = friendship.getFriend().getId();
        this.accepted = friendship.isAccepted();
    }
    public static FriendshipDTO fromEntity(Friendship friendship){
        return new FriendshipDTO(friendship);
    }
}
