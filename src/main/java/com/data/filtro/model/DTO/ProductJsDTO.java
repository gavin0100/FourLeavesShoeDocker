package com.data.filtro.model.DTO;

import com.data.filtro.model.Category;
import com.data.filtro.model.Material;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductJsDTO {
    private String id;

    private String productName;

    private Integer quantity;

    private Integer sold;

    private Integer price;
    private String materialId;

    private String description;

    private String image;

    private Instant createdDate;

    private Integer status;

    private Integer discount;

    private String categoryId;
}
