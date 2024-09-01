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
public class MyModel implements MyModelInterface{
    @Id
    private int id;
    private String name;
    private boolean status;

    private String avatar;

    @Override
    public String getUserName() {
        return "user name my model";
    }

    @Override
    public String getPassword() {
        return "password my model";
    }
}
