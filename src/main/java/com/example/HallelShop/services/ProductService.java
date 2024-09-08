package com.example.HallelShop.services;

import com.example.HallelShop.models.Image;
import com.example.HallelShop.models.Product;
import com.example.HallelShop.models.User;
import com.example.HallelShop.repositories.ProductRepositiry;
import com.example.HallelShop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepositiry productRepositiry;
   private final UserRepository userRepository;

    public List<Product> listProducts(String title) {
        if (title != null) return productRepositiry.findByTitle(title);

        return productRepositiry.findAll();
    }

    @Transactional
    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image image1 = null;
        Image image2 = null;
        Image image3 = null;

        log.info("Saving product. Title: {}, User email: {}", product.getTitle(), product.getUser().getEmail());

        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
            log.info("Added image1: {}", file1.getOriginalFilename());
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
            log.info("Added image2: {}", file2.getOriginalFilename());
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
            log.info("Added image3: {}", file3.getOriginalFilename());
        }

        Product pructFromDb = productRepositiry.save(product);
        pructFromDb.setPreviewImageId(pructFromDb.getImages().get(0).getId());
        productRepositiry.save(pructFromDb);

        log.info("Product saved with id: {}", pructFromDb.getId());
    }


    public  User getUserByPrincipal(Principal principal) {
        if(principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }


    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFilename(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }


    @Transactional
    public void deleteProduct(User user, Long id) {
        log.info("Attempting to delete product with id: {}", id);

        Product product = productRepositiry.findById(id).orElse(null);

        if (product != null) {
            log.info("Product found. Title: {}, User: {}", product.getTitle(), product.getUser().getEmail());

            if (product.getUser().getId().equals(user.getId())) {
                productRepositiry.delete(product);  // Удаление продукта также должно удалить связанные изображения
                log.info("Product with id = {} was deleted", id);
            } else {
                log.error("User: {} doesn't own the product with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Product with id = {} is not found", id);
        }
    }







    public Product getProductById(Long id) {
        return productRepositiry.findById(id).orElse(null);
    }
}