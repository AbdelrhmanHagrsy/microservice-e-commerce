package com.abdelrahman.customerservice.repository;

import com.abdelrahman.customerservice.entity.Customer;
import com.abdelrahman.customerservice.entity.CustomerPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT e FROM CustomerPayment e WHERE e.customer.id = :customerID")
    Set<CustomerPayment> getCustomerPaymentByCustomerId(@Param("customerID") Long customerID);
}
