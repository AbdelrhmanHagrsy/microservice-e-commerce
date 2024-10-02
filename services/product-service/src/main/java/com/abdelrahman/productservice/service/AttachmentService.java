package com.abdelrahman.productservice.service;

import com.abdelrahman.productservice.entity.Attachment;
import com.abdelrahman.productservice.entity.Product;
import com.abdelrahman.productservice.exception.EntityNotFound;
import com.abdelrahman.productservice.repository.AttachmentRepository;
import com.abdelrahman.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AmazonS3Service amazonS3Service;
    private final ProductRepository productRepository;

    public String uploadProductImage(Long productId, MultipartFile multipartFile) {

        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFound(String.format("Product with ID %s not found.", productId)));
            // upload file in amazon s3
            String fileName = amazonS3Service.uploadFile(multipartFile);
            // save attachment info in db
            Set<Attachment> attachments = product.getAttachments();
            Attachment newAttachment = Attachment.builder()
                    .originalFileName(multipartFile.getOriginalFilename())
                    .uniqueAwsFileName(fileName)
                    .product(product)
                    .build();
            attachments.add(newAttachment);
            attachmentRepository.save(newAttachment);
            productRepository.save(product);
            return "Image uploaded successfully ";
        } catch (EntityNotFound ex) {
            log.error(ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Failed to upload Image !");
        }
    }

    @Transactional
    public String deleteProductImage(String awsImageName) {
        try {

            Attachment attachment = attachmentRepository.findByUniqueAwsFileName(awsImageName)
                    .orElseThrow(() -> new EntityNotFound(String.format("File with name %s not found.", awsImageName)));
            // delete file from amazon s3
            amazonS3Service.deleteFile(awsImageName);
            // delete attachment from db
            attachmentRepository.deleteById(attachment.getId());
            return "Image deleted successfully ";
        } catch (EntityNotFound ex) {
            log.error(ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Failed to delete Image!");
        }
    }
}
