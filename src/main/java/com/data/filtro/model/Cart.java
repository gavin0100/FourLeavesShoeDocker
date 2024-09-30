package com.data.filtro.model;

import com.fasterxml.jackson.annotation.*;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;


import java.io.Serializable;
//import java.util.Date;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "giohang")
@Data
@EqualsAndHashCode(exclude = "user")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString(exclude = {"user", "cartItemList"})
public class Cart implements Serializable {

    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "magiohang")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "makh", referencedColumnName = "makh")
    private User user;

    @Column(name = "thoigiantao")
    private Instant createdDate;

    @Column(name = "thoigiancapnhat")
    private Instant updatedDate;

    @Column(name = "trangthai")
    private Integer status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartItem> cartItemList;
}
