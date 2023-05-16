package com.tzesh.ecommerceapi.entity;

import com.tzesh.ecommerceapi.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author tzesh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "PRODUCT_ID_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRODUCT_ID_GEN", sequenceName = "PRODUCT_ID_SEQ")
    private Long id;

    @Column(name = "CONTENT", length = 1000, nullable = false)
    private String content;

    @JoinColumn(name = "PRODUCT_ID", nullable = false, referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @JoinColumn(name = "USER_ID", nullable = false, referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "RATING", nullable = false)
    private Integer rating;
}
