package com.tzesh.ecommerceapi.entity;

import com.tzesh.ecommerceapi.entity.auth.Token;
import com.tzesh.ecommerceapi.enums.UserType;
import com.tzesh.ecommerceapi.enums.auth.RoleEnum;
import com.tzesh.ecommerceapi.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User is an entity for users
 * @author tzesh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_USER")
public class User extends BaseEntity implements UserDetails {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "USER_ID_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "USER_ID_GEN", sequenceName = "USER_ID_SEQ")
    private Long id;

    @Column(name = "USERNAME", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", length = 128, nullable = false)
    private String password;

    @Column(name = "EMAIL", length = 60, nullable = false, unique = true)
    private String email;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "TELEPHONE", length = 10, nullable = false, unique = true)
    private String telephone;

    @Column(name = "ROLE", length = 50)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name = "TYPE", length = 50)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
