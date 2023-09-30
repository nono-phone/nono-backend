package com.vn.aptech.smartphone.exception;

import com.vn.aptech.smartphone.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundListProductException extends RuntimeException{
    private final List<String> productsNotFound;

    public NotFoundListProductException(List<String> productsNotFound) {
        super(buildMessage(productsNotFound));
        this.productsNotFound = productsNotFound;
    }

    public List<String> getProductsNotFound() {
        return productsNotFound;
    }

    private static String buildMessage(List<String> productsNotFound) {
        StringBuilder errorMessage = new StringBuilder("Products not found: ");
        for (String productName : productsNotFound) {
            errorMessage.append(productName).append(", ");
        }
        // Remove the last comma and space
        errorMessage.setLength(errorMessage.length() - 2);
        return errorMessage.toString();
    }
}
