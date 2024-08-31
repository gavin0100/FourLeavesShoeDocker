package com.data.filtro.interview;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Table(name="model")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyModel {
    @Id
    private int id;
    private String name;
    private boolean status;

    private String avatar;
}
