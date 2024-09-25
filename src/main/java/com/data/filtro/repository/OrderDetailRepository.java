package com.data.filtro.repository;

import com.data.filtro.model.Order;
import com.data.filtro.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("select o from OrderDetail o where o.id =:orderId")
    List<OrderDetail> findOrderDetailByOrderId(@Param("orderId") long orderId);
}
