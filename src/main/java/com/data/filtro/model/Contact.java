package com.data.filtro.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

//import java.util.Date;

@Entity
@Table(name = "lienhe")
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id @Tsid
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ten")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "chude")
    private String subject;

    @Column(name = "tinnhan")
    private String message;
}
