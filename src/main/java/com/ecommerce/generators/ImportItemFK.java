package com.ecommerce.generators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ImportItemFK implements Serializable {
    @Column(name="import_id")
    private long importId;
    @Column(name = "product_id")
    private long productId;
    private String size;
    private String color;
}
