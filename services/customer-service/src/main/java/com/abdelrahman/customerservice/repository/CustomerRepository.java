package com.abdelrahman.customerservice.repository;

import com.abdelrahman.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
