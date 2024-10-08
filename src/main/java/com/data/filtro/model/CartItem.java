package com.data.filtro.model;

import com.fasterxml.jackson.annotation.*;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//import java.util.Date;
import java.time.Instant;

@Entity
@Table(name = "giohang_chitiet")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CartItem implements Serializable {

    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magiohang", referencedColumnName = "magiohang")
    @JsonBackReference
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "masp", referencedColumnName = "masp")
    private Product product;

    @Column(name = "soluong")
    private Integer quantity;

    @Column(name = "giatien")
    private Integer price;

    @Column(name = "tong")
    private Integer total;

    @Column(name = "thoigianmua")
    private Instant purchasedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magiohangtam", referencedColumnName = "id")
    private GuestCart guestCart;
}
