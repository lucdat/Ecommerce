package com.ecommerce.domain;

import com.ecommerce.generators.ImportItemFK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "import_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportItem {
    @EmbeddedId
    private ImportItemFK id;
    @ManyToOne
    @JoinColumn(name = "import_id",referencedColumnName = "id")
    @MapsId("importId")
    private Import anImport;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    @MapsId("productId")
    private Product product;
    @Column(columnDefinition = "int default 0")
    private int quantity;
    @Column(columnDefinition = "double precision")
    private Double price;
    @Column(insertable = false,updatable = false)
    private String size;
    @Column(insertable = false,updatable = false)
    private String color;
    private int gender;
    @Column(columnDefinition = "double precision")
    private double totalPrice;
}
