package com.data.filtro.api.restcontroller;

import com.data.filtro.exception.api.product.CantCommentException;
import com.data.filtro.exception.api.product.CantFindProductByProductIdException;
import com.data.filtro.exception.api.product.NotValidCommentException;
import com.data.filtro.model.Feedback;
import com.data.filtro.model.Product;
import com.data.filtro.model.User;
import com.data.filtro.service.FeedbackService;
import com.data.filtro.service.InputService;
import com.data.filtro.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/api/v1/product")
@RequiredArgsConstructor
public class ProductAPIRestController {
    private final ProductService productService;

    private final FeedbackService feedbackService;

    private final InputService inputService;
    private String errorMessage;


    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable Long id) {
        try{
            User user = getCurrentUser();
            long currentProductId = id;
            long maxProductId = productService.countAll();
            Product product = productService.getProductById(id);
            List<Feedback> feedbackList = feedbackService.getAllFeedBackByProductId(id);
            int numberOfFeedback = feedbackList.size();
            List<Product> productList = productService.getTop4ProductsByMaterial(product.getMaterial().getId(), currentProductId);

            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("product", product);
            response.put("numberOfFeedback", numberOfFeedback);
            response.put("products", productList);
            response.put("currentProductId", currentProductId);
            response.put("maxProductId", maxProductId);
            response.put("feedbackList", feedbackList);

            return ResponseEntity.ok(response);
        } catch (Exception ex){
            throw new CantFindProductByProductIdException(id);
        }
    }

    @PostMapping("/{id}/feedback")
    public ResponseEntity<Map<String, String>> addFeedback(
            @RequestParam String content,
            @RequestParam("numberOfStars") int numberOfStars,
            @RequestParam("_csrfParameterName") String csrfTokenForm,
            @PathVariable Long id) {

        if (!inputService.isValidComment(content)) {
            String message = "The comment content should only consist of lowercase letters, numbers, '@' symbol, parentheses, commas, periods, exclamation marks, and spaces.";
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("errorMessage", message);
            throw new NotValidCommentException(content);
        }
        try{
            Feedback feedback = new Feedback();
            feedback.setContent(content);
            feedback.setUser(getCurrentUser());
            feedback.setStars(numberOfStars);
            feedback.setProduct(productService.getProductById(id));
            feedbackService.addFeedback(feedback);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Feedback added successfully");
            return ResponseEntity.ok(successResponse);
        } catch (Exception ex){
            throw new CantCommentException(id);
        }

    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }
}
