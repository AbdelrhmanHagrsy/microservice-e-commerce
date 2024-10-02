package com.abdelrahman.orderservice.repository;

import com.abdelrahman.orderservice.dto.kafka.OrderStatus;
import com.abdelrahman.orderservice.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {

    List<Order> findAllByCustomerUserName(String userName);

    List<Order> findAllByCustomerUserNameAndOrderStatus(String userName, OrderStatus orderStatus);
}
