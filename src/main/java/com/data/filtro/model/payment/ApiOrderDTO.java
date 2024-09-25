package com.data.filtro.model.payment;

import lombok.*;

//import java.util.Date;
import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiOrderDTO {
    private String id;
    private String name;

    private Instant orderDate;

    private String email;
    private String phoneNumber;

    private String address;

    private String city;
    private Integer zip;
    private PaymentMethod paymentMethod;
    private Integer total;
    private OrderStatus statusPayment;
}
