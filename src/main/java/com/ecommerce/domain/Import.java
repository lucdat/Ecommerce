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
@Table(name="imports")
public class Import {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private int amount;
    @Column(columnDefinition = "double precision")
    private Double totalPrice;
    private String name;
    @Column(length = 200)
    private String description;
    @OneToMany(mappedBy = "anImport")
    private Collection<ImportItem> importItems = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
