package com.ecommerce.generators;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommentFK implements Serializable {
    @Column(name="product_id")
    private Long productId;
    @Column(name="user_id")
    private Long userId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date")
    private Date date;


}
