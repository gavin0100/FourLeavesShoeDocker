package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//import java.util.Date;
import java.time.Instant;
@Entity
@Table(name = "phanhoi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements Serializable {
    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "masp")
    @JsonManagedReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "makh")
    @JsonManagedReference
    private User user;

    @Column(name = "noidung")
    private String content;

    @Column(name = "ngayph")
    private Instant date;

    @Column(name = "sosao")
    private int stars;

}