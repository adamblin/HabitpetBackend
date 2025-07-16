package com.habitpet.app.habitpetbackend.domain;

import com.habitpet.app.habitpetbackend.domain.enums.FriendshipStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="friendships")
public class Friendship {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status;

    public Friendship() {}

    public Friendship(User user, User friend, FriendshipStatus status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

    // Getters and Setters

    public FriendshipStatus getStatus() {
        return status;
    }
    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
