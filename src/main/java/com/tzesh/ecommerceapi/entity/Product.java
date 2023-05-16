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
 * Product is an entity for products
 * @author tzesh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "PRODUCT_ID_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRODUCT_ID_GEN", sequenceName = "PRODUCT_ID_SEQ")
    private Long id;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 1000, nullable = false)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @JoinColumn(name = "OWNER_ID", nullable = false, referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;
}
