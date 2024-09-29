package com.data.filtro.model;

import com.data.filtro.model.DTO.UserJsDTO;
import com.data.filtro.model.payment.ApiOrderDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Collection;
//import java.util.Date;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "khachhang")
@Data
@EqualsAndHashCode(exclude = {"cart"})
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"cart", "invoices", "orders"})
public class User implements UserDetails, Serializable {
    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "makh")
    private Long id;

    @Column(name = "hoten")
    private String name;

    @Column(name = "ngaysinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Instant dob;

    @Column(name = "gioitinh")
    private String sex;

    @Column(name = "diachi")
    private String address;

    @Column(name = "zip")
    private Integer zip;

    @Column(name = "thanhpho")
    private String city;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String phoneNumber;

    @Column(name = "tinhtrang")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ma_vai_tro")
//    @JsonInclude(JsonInclude.Include.NON_NULL) // Include non-null fields
    private UserPermission userPermission;


    @Column(name = "tai_khoan")
    private String accountName;

    @Column(name = "mat_khau")
    private String password;

    @Column(name = "ngay_tao")
    private Instant createdDate;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name="otp")
    private String otp;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private Provider provider;



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
//    @JsonBackReference
    private List<Order> orders;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_"+userPermission.getRole().name()),
                new SimpleGrantedAuthority(userPermission.getCategoryManagement() +"_CATEGORY"),
                new SimpleGrantedAuthority(userPermission.getOrderManagement()+ "_ORDER"),
                new SimpleGrantedAuthority(userPermission.getProductManagement()+ "_PRODUCT"),
                new SimpleGrantedAuthority(userPermission.getUserManagement()+ "_USER"),
                new SimpleGrantedAuthority(userPermission.getStaffManagement()+ "_STAFF"),
                new SimpleGrantedAuthority(userPermission.getMaterialManagement()+ "_MATERIAL"),
                new SimpleGrantedAuthority(userPermission.getPlaceOrderManagement()+ "_PLACE_ORDER")
        );
    }


    @Override
    public String getUsername() {
        return getAccountName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserJsDTO convertToApiJsDTO(){
        return UserJsDTO.builder().
                id(String.valueOf(this.id))
                .name(this.name)
                .dob(this.dob)
                .sex(this.sex)
                .address(this.address)
                .zip(this.zip)
                .city(this.city)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .status(this.status)
                .userPermission_id(String.valueOf(this.userPermission.getPermissionId()))
                .accountName(this.accountName)
                .password(this.password)
                .build();

    }
}
