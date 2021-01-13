package com.example.toucan_remake.password_reset;

import com.example.toucan_remake.user.EntityUser;

import javax.persistence.*;
import java.util.UUID;

/*@Entity
@Table(name = "notes")*/
public class EntityPasswordReset {

    /*public EntityNote(){}

    public EntityNote(String title, String content, long creationTimestamp, EntityUser owner){
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
        this.owner = owner;
    }

    @Id
    @Column(name = "uuid_note", length = 16, unique = true, nullable = false)
    private UUID uuid = UUID.randomUUID();

    @Column(name =  "title", nullable = false)
    private String title;

    @Column(name =  "content", nullable = false)
    private String content;

    @Column(name = "creation_timestamp", nullable = false)
    private long creationTimestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private EntityUser owner;

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public EntityUser getOwner() {
        return owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }*/
}
