package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
//import java.util.Date;
import java.time.Instant;

@Entity
@Table(name = "nhanvien")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff implements Serializable {

    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manv")
    private Long id;

    @Column(name = "hoten")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngaysinh")
    private Instant dob;

    @Column(name = "gioitinh")
    private String sex;

    @Column(name = "sdt")
    private String phoneNumber;


    @Column(name = "tinhtrang")
    private Integer status;

    @Column(name = "tai_khoan")
    private String accountName;

    @Column(name = "mat_khau")
    private String password;

    @Column(name = "ngay_tao")
    private Instant createdDate;

    @Column(name = "ma_vai_tro")
    private Integer roleNumber;

    @Column(name = "password_reset_token")
    private String passwordResetToken;
}
