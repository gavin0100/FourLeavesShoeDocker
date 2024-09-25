package com.data.filtro.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

//import java.util.Date;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "hoadon")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mahoadon")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "makh", referencedColumnName = "makh")
    private User user;

    @Column(name = "ngaymua")
    private Instant purchasedDate;

    @Column(name = "tong")
    private Integer total;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;


}
