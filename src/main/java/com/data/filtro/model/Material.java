package com.data.filtro.model;

import com.data.filtro.model.DTO.MaterialJsDTO;
import com.fasterxml.jackson.annotation.*;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "vatlieu")
@Data
@AllArgsConstructor
@Component
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Material {
    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mavatlieu")
    private Long id;

    @Column(name = "tenvatlieu")
    private String materialName;

    @Column(name = "mota")
    private String description;

    @Column(name = "tinhtrang")
    private Integer status;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonBackReference(value = "material-product")
    private List<Product> products;

    public MaterialJsDTO convertToApiJsDTO(){
        return MaterialJsDTO.builder()
                .id(String.valueOf(this.id))
                .materialName(this.materialName)
                .description(this.description)
                .status(this.status)
                .build();
    }

}
