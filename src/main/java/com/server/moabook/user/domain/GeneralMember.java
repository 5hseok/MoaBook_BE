package com.server.moabook.user.domain;

import com.server.moabook.group.domain.Group;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class GeneralMember {
    @Id
    @Column(name = "userId")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(unique = true)
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "profile_image_url")
    private String profile_image_url;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "received_email")
    private boolean received_email;

    @Column(name = "sended_email")
    private boolean sended_email;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groups;

    public void updateUserUpdateTime(){
        this.updated_at = LocalDateTime.now();
    }

    public void updateReceivedEmail(boolean check){
        this.received_email = check;
    }

    public void updateSendedEmailTrue(){
        this.sended_email = true;
    }

    public void updateSendedEmailFalse(){
        this.sended_email = false;
    }

    public void updateEmail(String email){
        this.email = email;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}