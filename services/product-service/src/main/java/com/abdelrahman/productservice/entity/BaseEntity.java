package com.abdelrahman.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity<T>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public T id;
    @CreatedDate
    public LocalDate created;
    @CreatedBy
    public String createdBy;
    @LastModifiedDate
    public LocalDate lastModifiedBy;
    @LastModifiedBy
    public String modified;
}
