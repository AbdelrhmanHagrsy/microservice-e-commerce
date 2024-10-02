package com.abdelrahman.productservice.repository;

import com.abdelrahman.productservice.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

    Optional<Attachment> findByUniqueAwsFileName(String fileName);
}