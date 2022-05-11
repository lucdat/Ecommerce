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
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 45,unique = true,nullable = false)
    private String code;
    @Column(length = 50,nullable = false)
    private String name;
    @Column(columnDefinition = "boolean default false")
    private Boolean newProduct;
    @Column(columnDefinition = "boolean default false")
    private Boolean bestSeller;
    @Column(columnDefinition = "boolean default false")
    private Boolean hotProduct;
    private int gender;
    @Column(length = 150)
    private String shortDescription;
    @Column(columnDefinition = "text")
    private String detailDescription;
    @Column(nullable = false,columnDefinition = "double precision")
    private Double price;
    @Column(columnDefinition = "double precision")
    private Double competitivePrice;
    private int quantity;
    @Column(nullable = false,columnDefinition = "boolean default false")
    private Boolean activeFlag;
    @Temporal(TemporalType.DATE)
    private Date addAt;

    @ManyToOne
    @JoinColumn(columnDefinition = "cate_id",referencedColumnName = "id")
    private Category category;
    @ManyToOne
    @JoinColumn(columnDefinition = "brand_id",referencedColumnName = "id")
    private Brand brand;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "product_color",
            joinColumns = {@JoinColumn(name="product_id")},
            inverseJoinColumns = {@JoinColumn(name="color_id")}
    )
    private Collection<Color> colors = new HashSet<>();
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "product_size",
            joinColumns = {@JoinColumn(name="product_id")},
            inverseJoinColumns = {@JoinColumn(name="size_id")}
    )
    private Collection<Size> sizes = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Collection<ImportItem> importItems = new HashSet<>();
    @OneToMany(mappedBy = "product")
    private Collection<OrderItem> orderItems = new HashSet<>();
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "product_tag",
            joinColumns = {@JoinColumn(name="product_id")},
            inverseJoinColumns = {@JoinColumn(name="tag_id")}
    )
    private Collection<Tag> tags = new HashSet<>();
    @OneToMany(mappedBy = "product")
    private Collection<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Collection<Image> images = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "discount_product",
            joinColumns = {@JoinColumn(name="product_id")},
            inverseJoinColumns = {@JoinColumn(name="discount_id")}
    )
    private Collection<Discount> discounts = new HashSet<>();
}
