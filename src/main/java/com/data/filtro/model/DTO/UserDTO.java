package com.data.filtro.model.DTO;

import com.data.filtro.model.Cart;
import com.data.filtro.model.Invoice;
import com.data.filtro.model.Order;
import com.data.filtro.model.UserPermission;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
//import java.util.Date;
import java.time.Instant;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements Serializable {
    private Long id;
    private String name;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date dob;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Instant dob;

    @NotBlank
    private String sex;

    @NotBlank
    private String address;

    @NotNull
    private Integer zip;

    @NotBlank
    private String city;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d+")
    @Size(min = 10, max = 11)
    private String phoneNumber;

    @NotNull
    private Integer status;

    @NotNull
    private Long userPermissionId;
    @NotBlank
    private String accountName;
    @NotBlank
    private String password;

}
