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
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50,nullable = false)
    private String name;
    @Column(length = 50,nullable = false,unique = true)
    private String username;
    @Column(length = 200,nullable = false)
    private String password;
    @Column(length = 15,unique = true)
    private String phone;
    @Column(length = 100,unique = true)
    private String email;
    @Column(columnDefinition = "boolean default false")
    private Boolean activeFlag;

    @OneToMany(mappedBy = "user")
    private Collection<Comment> comments = new HashSet<>();
    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    private Collection<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private Collection<Orders> orders = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private Collection<Import> imports = new HashSet<>();
}
