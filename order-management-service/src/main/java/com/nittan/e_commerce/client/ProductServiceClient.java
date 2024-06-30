package com.nittan.e_commerce.client;

import com.nittan.e_commerce.dto.OrderResponseDto;
import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.exception.ProductServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class ProductServiceClient {

    @Autowired
    private RestTemplate template;

    public ResponseEntity<List<Product>> getProductsForOrder(String orderId) {
        List<Product> products=null;
        try {
            products = template.getForObject("http://PRODUCT-CATALOG-SERVICE/product/getProductsForOrder/", List.class);

        } catch (HttpServerErrorException errorException) {
            log.error("ProductServiceClient::Getting products caught the HttpServer server error {}", errorException.getResponseBodyAsString());
            throw new ProductServiceException(errorException.toString());
        } catch (Exception ex) {
            log.error("ProductServiceException::Generice exception caught the generic error {}", ex.getMessage());
            throw new ProductServiceException(ex.toString());

        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}




/*

package com.nittan.e_commerce.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


 */