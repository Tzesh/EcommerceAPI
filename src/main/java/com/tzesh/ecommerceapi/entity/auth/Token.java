package com.tzesh.ecommerceapi.entity.auth;

import com.tzesh.ecommerceapi.base.entity.BaseEntity;
import com.tzesh.ecommerceapi.entity.User;
import com.tzesh.ecommerceapi.enums.auth.TokenEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Token entity for storing tokens in database
 * @author tzesh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TOKEN")
public class Token extends BaseEntity {
    @Id
    @GeneratedValue(generator = "TOKEN_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "TOKEN_GEN", sequenceName = "TOKEN_SEQ", allocationSize = 50)
    public Long id;

    @Column(name = "TOKEN", unique = true)
    public String token;

    @Column(name = "TYPE", length = 50)
    @Enumerated(EnumType.STRING)
    public TokenEnum tokenType = TokenEnum.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public User user;
}
