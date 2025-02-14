package com.habitpet.app.habitpetbackend.application.dto;

import com.habitpet.app.habitpetbackend.domain.Friendship;

public class FriendshipDTO {

    private String userUsername;
    private String friendUsername;
    private boolean accepted;

    public FriendshipDTO() {}

    public FriendshipDTO(Friendship friendship) {
        this.userUsername = friendship.getUser().getUsername();
        this.friendUsername = friendship.getFriend().getUsername();
        this.accepted = friendship.isAccepted();
    }

    public FriendshipDTO(String userUsername, String friendUsername, boolean accepted) {
        this.userUsername = userUsername;
        this.friendUsername = friendUsername;
        this.accepted = accepted;
    }
    public FriendshipDTO(String friendUsername, boolean accepted) {
        this.friendUsername = friendUsername;
        this.accepted = accepted;
    }

    public static FriendshipDTO fromEntity(Friendship friendship) {
        return new FriendshipDTO(friendship);
    }

    public String getUserUsername() {
        return userUsername;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
