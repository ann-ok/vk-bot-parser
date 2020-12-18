package org.vkbot.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User
{
    @Id
    private int id;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    private boolean subscribed;

    public User() {}

    public User(int id) {
        this.id = id;
        lastUpdate = Timestamp.valueOf(LocalDateTime.now());
        subscribed = false;
    }

    public User(int id, boolean subscribed) {
        this(id);
        this.subscribed = subscribed;
    }

    public int getId() {
        return id;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
