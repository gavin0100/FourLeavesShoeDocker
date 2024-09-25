package com.data.filtro.interview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<MyModel, Long> {
    List<MyModel> findAll();
}
