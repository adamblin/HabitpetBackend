package com.habitpet.app.habitpetbackend.domain;

import com.habitpet.app.habitpetbackend.application.dto.FriendshipDTO;
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
    @Column(nullable = false)
    private boolean accepted;

    public Friendship() {}

    public Friendship(User user, User friend, boolean accepted) {
        this.user = user;
        this.friend = friend;
        this.accepted = accepted;
    }

    // Getters and Setters

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
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
