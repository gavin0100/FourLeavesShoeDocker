package com.data.filtro.model;


import com.data.filtro.model.DTO.ProductJsDTO;
import com.data.filtro.model.DTO.UserJsDTO;
import com.fasterxml.jackson.annotation.*;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
//import java.util.Date;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "sanpham")
@Data
@ToString(exclude = {"material", "category", "cartItemList"})
// muốn loại bỏ khỏi kết quả của phương thức toString()
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "masp")
    private Long id;

    @Column(name = "tensanpham")
    private String productName;

    @Column(name = "soluong")
    private Integer quantity;

    @Column(name = "daban")
    private Integer sold;

    @Column(name = "giatien")
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mavatlieu", referencedColumnName = "mavatlieu")
    @JsonBackReference(value = "material-product")
    private Material material;

    @Column(name = "mota")
    private String description;

    @Column(name = "anh")
    private String image;

    @Column(name = "ngaytao")
    private Instant createdDate;

    @Column(name = "tinhtrang")
    private Integer status;

    @Column(name = "giamgia")
    private Integer discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "madanhmuc", referencedColumnName = "madanhmuc")
    @JsonBackReference(value = "category-product")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CartItem> cartItemList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InvoiceDetail> invoiceDetails;

    public ProductJsDTO convertToApiJsDTO(){
        return ProductJsDTO.builder().
                id(String.valueOf(this.id))
                .productName(this.productName)
                .quantity(this.quantity)
                .sold(this.sold)
                .price(this.price)
                .materialId(String.valueOf(this.material.getId()))
                .description(this.description)
                .image(this.image)
                .createdDate(this.createdDate)
                .status(this.status)
                .discount(discount)
                .categoryId(String.valueOf(this.category.getId()))
                .build();

    }
}
