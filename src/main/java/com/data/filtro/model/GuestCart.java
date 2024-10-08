package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.util.Date;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "giohang_temp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "thoigiantao")
    private Instant createdDate;


    @Column(name = "thoigiancapnhat")
    private Instant updatedDate;

    @OneToMany(mappedBy = "guestCart", cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;

}
