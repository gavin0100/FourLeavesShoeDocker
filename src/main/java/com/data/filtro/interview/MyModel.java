package com.data.filtro.interview;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="model")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyModel implements MyModelInterface{
    // @Tsid là cách viết tắt để sử dụng TsidGenerator
    // kiểu truyền thống thì phải viết @GenericGenerator và @GeneratedValue. để sài generator tùy chỉnh trong Hibernate.
    @Id @Tsid
//    @GeneratedValue(generator = "tsid")
//    @GenericGenerator(name = "tsid",
//            strategy = "io.hypersistence.utils.hibernate.id.TsidGenerator")
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
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
