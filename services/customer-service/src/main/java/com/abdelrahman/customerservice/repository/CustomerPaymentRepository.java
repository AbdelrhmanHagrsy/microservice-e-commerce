package com.abdelrahman.customerservice.repository;

import com.abdelrahman.customerservice.entity.CustomerPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPaymentRepository extends JpaRepository<CustomerPayment,Long> {
}
