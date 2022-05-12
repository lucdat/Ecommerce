package com.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
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
    @Column(length = 200,nullable = false)
    private String address;
    @Column(length = 200)
    private String note;
    @Column(columnDefinition = "int default 0")
    private int amount;
    @Column(columnDefinition = "double precision")
    private double totalPrice;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "status",columnDefinition = "varchar(50) default 'PENDING'")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order")
    private Collection<OrderItem> orderItems = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
