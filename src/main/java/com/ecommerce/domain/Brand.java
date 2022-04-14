package com.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length =45,unique = true,nullable = false)
    private String name;
    @Lob
    private String logo;

    @Temporal(TemporalType.DATE)
    private Date createAt;

    @OneToMany(mappedBy = "brand",fetch = FetchType.EAGER)
    private Collection<Product> products = new HashSet<>();
}
