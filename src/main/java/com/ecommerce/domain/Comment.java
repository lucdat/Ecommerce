package com.ecommerce.domain;

import com.ecommerce.generators.CommentFK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="comments")
public class Comment implements Serializable {
    @EmbeddedId
    private CommentFK id;
    @Column(length = 200,nullable = false)
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date",insertable = false,updatable = false)
    private Date date;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
