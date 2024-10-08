package com.data.filtro.model;

import com.data.filtro.model.payment.ApiOrderDTO;
import com.data.filtro.model.payment.OrderStatus;
import com.data.filtro.model.payment.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//import java.util.Date;
import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "dathang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "madathang")
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "makh", referencedColumnName = "makh")
    @JsonManagedReference
    private User user;

    @Column(name = "ngaydathang")
    private Instant orderDate;

    @Column(name = "diachi")
    @NotBlank
    private String address;

    @Column(name = "zip")
    @NotNull
    private Integer zip;

    @Column(name = "thanhpho")
    @NotBlank
    private String city;


    @Column(name = "SDT")
    @NotBlank
    private String phoneNumber;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "tong")
    @NotNull
    private Integer total;


    @Column(name = "tinhtrang")
    private Integer status;

    @Column(name = "trang_thai")
    @Enumerated(EnumType.STRING)
    private OrderStatus statusPayment;
    @Column(name = "phuongthucthanhtoan")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "order_code")
    private String order_code;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "orderId")
//    @JsonIgnore
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

    public ApiOrderDTO convertToApiDTO(){
        return ApiOrderDTO.builder().
                id(String.valueOf(this.id))
                .name(this.user.getName())
                .orderDate(this.orderDate)
                .email(this.email)
                .phoneNumber(this.getPhoneNumber())
                .address(this.address)
                .city(this.city)
                .zip(this.zip)
                .paymentMethod(this.paymentMethod)
                .total(this.total)
                .statusPayment(this.statusPayment)
                .build();

    }

}
