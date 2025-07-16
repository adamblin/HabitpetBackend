package com.habitpet.app.habitpetbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.habitpet.app.habitpetbackend.domain.Friendship;
import com.habitpet.app.habitpetbackend.domain.enums.FriendshipStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendshipDTO {

    private String userUsername;
    private String friendUsername;
    private FriendshipStatus status;

    public FriendshipDTO() {}

    public FriendshipDTO(Friendship friendship) {
        this.userUsername = friendship.getUser().getUsername();
        this.friendUsername = friendship.getFriend().getUsername();
        this.status = friendship.getStatus();
    }

    public FriendshipDTO(String userUsername, String friendUsername, FriendshipStatus status) {
        this.userUsername = userUsername;
        this.friendUsername = friendUsername;
        this.status = status;
    }
    public FriendshipDTO(String friendUsername, FriendshipStatus status) {
        this.friendUsername = friendUsername;
        this.status = status;
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

    public FriendshipStatus getStatus() {
        return status;
    }
}
