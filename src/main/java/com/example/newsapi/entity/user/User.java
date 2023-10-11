package com.example.newsapi.entity.user;

import com.example.newsapi.entity.AbstractEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "news_users")
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class User extends AbstractEntity implements UserDetails {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String parentName;
    private LocalDate creationDate;
    private LocalDate lastEditDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
