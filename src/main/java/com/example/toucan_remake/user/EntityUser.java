package com.example.toucan_remake.user;

import com.example.toucan_remake.note.EntityNote;
import com.example.toucan_remake.password_reset.EntityPasswordReset;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class EntityUser {

    public EntityUser(){}

    public EntityUser(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Id
    @Type(type="org.hibernate.type.PostgresUUIDType")
    @Column(name = "uuid_user", length = 16, unique = true, nullable = false)
    private UUID uuid = UUID.randomUUID();

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_reset_token", referencedColumnName = "uuid_password_reset")
    private EntityPasswordReset passwordResetToken;*/

    @OneToMany(targetEntity= EntityNote.class, mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<EntityNote> noteList = new ArrayList<>();

    public UUID getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    /*public EntityPasswordReset getPasswordResetToken() {
        return passwordResetToken;
    }*/

    public List<EntityNote> getNoteList() {
        return noteList;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public void setPasswordResetToken(EntityPasswordReset passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }*/

    public void setNoteList(List<EntityNote> noteList) {
        this.noteList = noteList;
    }
}
