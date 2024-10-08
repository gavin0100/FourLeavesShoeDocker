package com.data.filtro.service;

import com.data.filtro.Util.JsonConverter;
import com.data.filtro.interview.impl.BaseRedisService;
import com.data.filtro.model.Product;
import com.data.filtro.repository.ProductRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
//import java.util.Date;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private final BaseRedisService baseRedisService;

    @Value("${spring.data.minio.bucketName}")
    private String bucketName;

    @Value("${spring.data.minio.url}")
    private String url;

    @Value("${spring.data.minio.url_host_image}")
    private String urlHostImage;

    @Value("${spring.data.app.resetTopSellingProductByDays}")
    private String resetTopSellingProductByDays;

    @Value("${spring.data.app.resetTopSellingProductByHours}")
    private String resetTopSellingProductByHours;
    private final MinioClient minioClient;

    private final String PREFIX_DETAILED_PRODUCT = "detailed_product:";


    public void save(Product product) {
        productRepository.save(product);
    }


    public void addProduct(Product product) throws Exception {
        product.setCreatedDate(Instant.now());
        productRepository.save(product);
    }
    public void addProductWithImage(Product product, MultipartFile avatarFile) throws Exception {
        try {
            if (!avatarFile.isEmpty()){

                updateAvatarToMinIO(avatarFile);
                String avatarLink = urlHostImage + bucketName+ "/" + avatarFile.getOriginalFilename();
                product.setImage(avatarLink);
            }
        } catch (Exception ex){
            log.error("ProductService.java addProductWithImage: Can't upload image {} to product has id {}", avatarFile.getOriginalFilename(), product.getId());
        }
        product.setCreatedDate(Instant.now());
        productRepository.save(product);
        baseRedisService.delete("top_sixth_products");
    }


    public void update(Product product, MultipartFile avatarFile) throws Exception {

        Product existingProduct = productRepository.findById(product.getId()).get();

        // Update the existing product's properties with the new product's properties
        existingProduct.setProductName(product.getProductName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setSold(product.getSold());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setMaterial(product.getMaterial());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setStatus(product.getStatus());
        existingProduct.setDiscount(product.getDiscount());

//        existingProduct.setImage(product.getImage());
        try {
            if (!avatarFile.isEmpty()){
                if (existingProduct.getImage().contains("fourleavesshoedocker")){
                    String oldFileName = existingProduct.getImage().substring(existingProduct.getImage().lastIndexOf('/') + 1);
                    deleteAvatarToMinIO(oldFileName);
                }
                updateAvatarToMinIO(avatarFile);
                String avatarLink = urlHostImage + bucketName+ "/" + avatarFile.getOriginalFilename();
                existingProduct.setImage(avatarLink);
            }
        } catch (Exception ex){
            log.error("Can't upload image {} to product has id {}", avatarFile.getOriginalFilename(), product.getId());
        }
        productRepository.save(existingProduct);
        baseRedisService.delete("top_fourth_discount_products");
        baseRedisService.delete(PREFIX_DETAILED_PRODUCT + String.valueOf(existingProduct.getId()));
    }

    public void updateAvatarToMinIO(MultipartFile avatarFile){
        try {
            InputStream inputStream = avatarFile.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(avatarFile.getOriginalFilename())
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
        } catch (Exception e) {
            log.error("Failed to upload file.");
        }
    }
    public void deleteAvatarToMinIO(String fileName){
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            log.error("Failed to delete file.");
        }
    }



    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(long id) {
        Product product;
        if (baseRedisService.hasKey(PREFIX_DETAILED_PRODUCT + id)){
            String jsonProduct = (String) baseRedisService.get(PREFIX_DETAILED_PRODUCT + id);
            product = JsonConverter.convertJsonToProduct(jsonProduct);
            return product;
        } else {
            product = productRepository.findById(id);
            String json = JsonConverter.convertToJsonProduct(product);
            baseRedisService.set(PREFIX_DETAILED_PRODUCT + id, json);
        }
        return product;
    }

    @Transactional
    public List<Product> getAll() {
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            Hibernate.initialize(product.getCategory());
        }
        return productList;
    }

    @Transactional
    public List<Product> getTopSellingProducts() {
        List<Product> productList = new ArrayList<>();
        if (baseRedisService.hasKey("top_selling_products")){
            String discountProductsJson = (String) baseRedisService.get("top_selling_products");
            List<Product> productList1 = JsonConverter.convertJsonToListProduct(discountProductsJson);
            for (Product product : productList1) {
                Hibernate.initialize(product.getCategory());
            }
            return  productList1;
        }
        productList = productRepository.findTop8SellingProducts();
        String json = JsonConverter.convertListToJsonProduct(productList);
        baseRedisService.set("top_selling_products", json);
        if (Integer.parseInt(resetTopSellingProductByDays) != 0){
            baseRedisService.setTimeToLivedByDays("top_selling_products", Integer.parseInt(resetTopSellingProductByDays));
        }
        if (Integer.parseInt(resetTopSellingProductByHours) != 0){
            baseRedisService.setTimeToLivedByHours("top_selling_products", Integer.parseInt(resetTopSellingProductByHours));
        }

        for (Product product : productList) {
            Hibernate.initialize(product.getCategory());
        }

        return productList;
    }

    public List<Product> getSixthProducts(){
        List<Product> productList = new ArrayList<>();
        if (baseRedisService.hasKey("top_sixth_products")){
            String sixthProductsJson = (String) baseRedisService.get("top_sixth_products");
            List<Product> productList1 = JsonConverter.convertJsonToListProduct(sixthProductsJson);
            for (Product product : productList1) {
                Hibernate.initialize(product.getCategory());
            }
            return  productList1;
        }
        productList = productRepository.find6thProducts();
        String json = JsonConverter.convertListToJsonProduct(productList);
        baseRedisService.set("top_sixth_products", json);
        for (Product product : productList) {
            Hibernate.initialize(product.getCategory());
        }

        return productList;
    }
    public List<Product> getTopDiscountProducts() {
        List<Product> productList = new ArrayList<>();
        if (baseRedisService.hasKey("top_fourth_discount_products")){
            String top4ProductsJson = (String) baseRedisService.get("top_fourth_discount_products");
            List<Product> productList1 = JsonConverter.convertJsonToListProduct(top4ProductsJson);
            for (Product product : productList1) {
                Hibernate.initialize(product.getCategory());
            }
            return  productList1;
        }
        productList = productRepository.findTop4MostDiscountProducts();
        String json = JsonConverter.convertListToJsonProduct(productList);
        baseRedisService.set("top_fourth_discount_products", json);
        for (Product product : productList) {
            Hibernate.initialize(product.getCategory());
        }

        return productList;
    }


    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> getAvailableProducts(int status){
        return productRepository.availableProducts(status);
    }

    public List<Product> getDiscountProducts(){
        return productRepository.discountProducts();
    }

    public Page<Product> getProductByPriceAndMaterial(int lowPrice, int highPrice, long materialId, Pageable pageable){
        return productRepository.getProductByPriceAndMaterial(lowPrice, highPrice, materialId,pageable);
    }
    public Page<Product> getProductByPrice(int lowPrice, int highPrice, Pageable pageable){
        return productRepository.getProductByPrice(lowPrice, highPrice,pageable);
    }
    public Page<Product> getProductByMaterial( long materialId, Pageable pageable){
        return productRepository.getProductByMaterial(materialId,pageable);
    }
    public Page<Product> getProductByCategoryAndPriceAndMaterial(long categoryId, int lowPrice, int highPrice, long materialId, Pageable pageable){
        return productRepository.getProductByCategoryAndPriceAndMaterial(categoryId, lowPrice, highPrice, materialId,pageable);
    }
    public Page<Product> getProductByCategoryAndPrice(long categoryId, int lowPrice, int highPrice, Pageable pageable){
        return productRepository.getProductByCategoryAndPrice(categoryId, lowPrice, highPrice, pageable);
    }
    public Page<Product> getProductByCategoryAndMaterial(long categoryId, long materialId, Pageable pageable){
        return productRepository.getProductByCategoryAndMaterial(categoryId, materialId,pageable);
    }

    public Page<Product> getProductByCategory(long id, Pageable pageable) {
        return productRepository.findProductsByCategory(id, pageable);
    }

    public Page<Product> getProductByConditions(long id, int lowPrice, int highPrice, int materialId, Pageable pageable) {
        return productRepository.findProductsByCategory(id, pageable);
    }

    public Page<Product> getProductsByFlavorId(long id, Pageable pageable) {
        return productRepository.findProductsByFlavor(id, pageable);
    }


    public Page<Product> getProductsByName(String name, Pageable pageable) {
        return productRepository.findProducsByName(name, pageable);
    }

    public long countAll() {
        return productRepository.findAll().stream().count();
    }

    public List<Product> getTop4ProductsByMaterial(long id, long currentProductId) {
        return productRepository.findTop4ProductsByFlavor(id, currentProductId);
    }

    public List<Product> findProductByPrice(int priceLow, int priceHigh){
        return productRepository.findProductByPrice(priceLow, priceHigh);
    }
}
