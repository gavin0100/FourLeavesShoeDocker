package com.data.filtro.service;

import com.data.filtro.Util.JsonConverter;
import com.data.filtro.interview.BaseRedisService;
import com.data.filtro.interview.MyModel;
import com.data.filtro.model.Account;
import com.data.filtro.model.Product;
import com.data.filtro.repository.ProductRepository;
import com.github.kristofa.brave.internal.zipkin.internal.moshi.Json;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BaseRedisService baseRedisService;

    @Value("${spring.data.minio.bucketName}")
    private String bucketName;

    @Value("${spring.data.minio.url}")
    private String url;

    @Value("${spring.data.minio.url_host_image}")
    private String urlHostImage;
    @Autowired
    private MinioClient minioClient;

    public static final String HASH_KEY = "products"; // Key cho hash


    public void save(Product product) {
        productRepository.save(product);
    }


    public void addProduct(Product product) throws Exception {
        product.setCreatedDate(new Date());
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
        product.setCreatedDate(new Date());
        productRepository.save(product);
    }


    public void update(Product product, MultipartFile avatarFile) throws Exception {

        Product existingProduct = productRepository.findById(product.getId()).orElseThrow(null);

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



    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id);
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
//        if (baseRedisService.hasKey("key_discounted_products")){
//            List<Object> productListJson = baseRedisService.getList("key_discounted_products");
//            for (Product p: productList){
//                System.out.println(p.getProductName());
//            }
//            return  productList;
//        }
        productList = productRepository.findTop8SellingProducts();
        String json = JsonConverter.convertListToJsonProduct(productList);
        System.out.println(json);
//        List<Product> productList1 = JsonConverter.convertJsonToListProduct(json);

//        baseRedisService.setList("key_discounted_products", productList);
        for (Product product : productList) {
            Hibernate.initialize(product.getCategory());
        }

        return productList;
    }

    public List<Product> getSixthProducts(){
        List<Product> productList = productRepository.find6thProducts();
        return productList;
    }
    public List<Product> getTopDiscountProducts() {
        List<Product> productList = productRepository.findTop4MostDiscountProducts();
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

    public Page<Product> getProductByPriceAndMaterial(int lowPrice, int highPrice, int materialId, Pageable pageable){
        return productRepository.getProductByPriceAndMaterial(lowPrice, highPrice, materialId,pageable);
    }
    public Page<Product> getProductByPrice(int lowPrice, int highPrice, Pageable pageable){
        return productRepository.getProductByPrice(lowPrice, highPrice,pageable);
    }
    public Page<Product> getProductByMaterial( int materialId, Pageable pageable){
        return productRepository.getProductByMaterial(materialId,pageable);
    }
    public Page<Product> getProductByCategoryAndPriceAndMaterial(int categoryId, int lowPrice, int highPrice, int materialId, Pageable pageable){
        return productRepository.getProductByCategoryAndPriceAndMaterial(categoryId, lowPrice, highPrice, materialId,pageable);
    }
    public Page<Product> getProductByCategoryAndPrice(int categoryId, int lowPrice, int highPrice, Pageable pageable){
        return productRepository.getProductByCategoryAndPrice(categoryId, lowPrice, highPrice, pageable);
    }
    public Page<Product> getProductByCategoryAndMaterial(int categoryId, int materialId, Pageable pageable){
        return productRepository.getProductByCategoryAndMaterial(categoryId, materialId,pageable);
    }

    public Page<Product> getProductByCategory(int id, Pageable pageable) {
        return productRepository.findProductsByCategory(id, pageable);
    }

    public Page<Product> getProductByConditions(int id, int lowPrice, int highPrice, int materialId, Pageable pageable) {
        return productRepository.findProductsByCategory(id, pageable);
    }

    public Page<Product> getProductsByFlavorId(int id, Pageable pageable) {
        return productRepository.findProductsByFlavor(id, pageable);
    }


    public Page<Product> getProductsByName(String name, Pageable pageable) {
        return productRepository.findProducsByName(name, pageable);
    }

    public long countAll() {
        return productRepository.findAll().stream().count();
    }

    public List<Product> getTop4ProductsByMaterial(int id, int currentProductId) {
        return productRepository.findTop4ProductsByFlavor(id, currentProductId);
    }

    public List<Product> findProductByPrice(int priceLow, int priceHigh){
        return productRepository.findProductByPrice(priceLow, priceHigh);
    }
}
