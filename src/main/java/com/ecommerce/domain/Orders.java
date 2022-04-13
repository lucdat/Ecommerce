package com.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50,nullable = false)
    private String name;
    @Column(length = 15,nullable = false)
    private String phone;
    @Column(length = 50)
    private String email;
    @Column(length = 200,nullable = false)
    private String address;
    @Column(length = 200)
    private String note;
    @Column(columnDefinition = "int default 0")
    private int amount;
    @Column(columnDefinition = "double precision")
    private double totalPrice;
    @Column(name = "status",columnDefinition = "varchar(50) default 'PENDING'")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order")
    private Collection<OrderItem> orderItems = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
