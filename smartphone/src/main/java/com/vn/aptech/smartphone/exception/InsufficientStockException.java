package com.vn.aptech.smartphone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientStockException extends RuntimeException{
    private final List<String> productId;

    public InsufficientStockException(List<String> productsNotFound) {
        super(buildMessage(productsNotFound));
        this.productId = productsNotFound;
    }

    public List<String> getProductsNotFound() {
        return productId;
    }

    private static String buildMessage(List<String> productsNotFound) {
        StringBuilder errorMessage = new StringBuilder("Insufficient stock for products in the order: ");
        for (String productName : productsNotFound) {
            errorMessage.append(productName).append(", ");
        }
        // Remove the last comma and space
        errorMessage.setLength(errorMessage.length() - 2);
        return errorMessage.toString();
    }
}
