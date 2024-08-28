package com.data.filtro.Util;

import com.data.filtro.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    public static String convertListToJsonProduct(List<Product> products) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(products);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Product> convertJsonToListProduct(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Product> products = new ArrayList<>();
//            List<Product> products = objectMapper.readValue(json, new TypeReference<List<Product>>() {});
//            for (Product product : products) {
//                System.out.println(product.getProductName());
//            }
            String json1 = "{\"id\":6,\"productName\":\"Rocket Dog Women's Sheriff Boot\",\"quantity\":115,\"sold\":134,\"price\":50,\"material\":{\"id\":3,\"materialName\":\"Canvas\",\"description\":\"Lightweight and breathable\",\"status\":1},\"description\":\"Synthetic Rubber sole. Shaft measures approximately 8 from arch. Heel measures approximately 2.5\\\". Boot opening measures approximately 13.5\\\" around. Western-inspired style boot. \",\"image\":\"https://m.media-amazon.com/images/I/91ABavds1pL._AC_UY395_.jpg\",\"createdDate\":1678726800000,\"status\":1,\"discount\":0,\"category\":{\"id\":3,\"categoryName\":\"Boots\",\"status\":1}}";
            Product product = objectMapper.readValue(json1, Product.class);
            System.out.println(product.toString());
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

