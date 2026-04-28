package com.xaviervinicius.labschedule.models.userModel;

import com.xaviervinicius.labschedule.models.scheduleModel.ScheduleModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table (name = "tb_user")
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "scheduler", fetch = FetchType.LAZY)
    private List<ScheduleModel> schedules = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountState state;

    @Column
    @JsonIgnore
    private Instant lastVerificationCodeRequest;

    @CreationTimestamp
    private Instant createdAt;

    public UserModel(UUID id){
        this.id = id;
    }

    public boolean isAdmin(){return this.role == Role.ADMIN;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return isAdmin() ?
                List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")) :
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isEnabled() {
        return this.state == AccountState.ACTIVE;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
