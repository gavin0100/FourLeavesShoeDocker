package com.data.filtro.model.DTO;

import com.data.filtro.model.UserPermission;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJsDTO {
    private String id;
    private String name;
    private Instant dob;
    private String sex;
    private String address;

    private Integer zip;
    private String city;

    private String email;

    private String phoneNumber;

    private Integer status;
    private String userPermission_id;

    private String accountName;

    private String password;

}
