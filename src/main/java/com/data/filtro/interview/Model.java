package com.data.filtro.interview;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
// @Entity: In Spring Boot, an entity is a class that represents a table in a database.
//In Spring Boot, the @Entity annotation is used to specify that a class is an entity /ˈen.tɪ.ti/
// and is mapped to a database /ˈdeɪ.tə.beɪs/ table. This is part of the Java Persistence API (JPA).
// When you annotate a class with @Entity, it means that the class represents a table
// stored in a database, and each instance of the class corresponds to a row in that table

// Lombok
//Lombok is a Java library that helps reduce boilerplate /ˈbɔɪ.lə.pleɪt/ code by automatically /ˌɔː.təˈmæt.ɪ.kəl.i/
// generating common methods like getters, setters, constructors, and more through annotations.
// This can make your code cleaner and easier to maintain.
//
//In a Spring Boot application, Lombok can be particularly useful.
// Here are some common Lombok annotations and their uses:
//
//     @Getter and @Setter: Automatically generate getter and setter methods for all fields.
//     @NoArgsConstructor, @AllArgsConstructor, and @RequiredArgsConstructor:
//          Generate constructors with no arguments, all arguments, or required arguments
//          (final fields and fields with constraints), respectively.
//     @Data: A shortcut for @ToString, @EqualsAndHashCode, @Getter, @Setter, and @RequiredArgsConstructor all in one. (a shortcut for: 1 phím tắt)
//     @Builder: Implements the builder pattern for your class.
//     @Slf4j: Creates a logger field in the class for logging purposes. /ˈpɜːrpəs/


//
@Entity
@Table(name="model")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    @Id
    private int id;
    private String name;
    private boolean status;
}
