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
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 45,unique = true,nullable = false)
    private String name;
    @Temporal(TemporalType.DATE)
    private Date createAt;
    @OneToMany(mappedBy = "category")
    private Collection<Product> products = new HashSet<>();

}
