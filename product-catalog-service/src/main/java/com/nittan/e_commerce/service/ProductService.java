package com.nittan.e_commerce.service;

import com.nittan.e_commerce.dao.ProductDao;
import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.exception.GenericeException;
import com.nittan.e_commerce.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    Logger logger= LoggerFactory.getLogger(ProductService.class);

    // getting all products
    public List<Product> getAllProducts(){
        List<Product> products =  productDao.findAll();
        if(products.isEmpty()){
            logger.warn("No products found");
            throw new ProductNotFoundException("No products found");
        }
        logger.info("Retrieved {} products " + products.size());
        return products;
    }

    // get product by id
    public Product getProductById(Long id) {
        try{
            return productDao.findById(id).get();
        }
        catch(Exception exception){
            throw new ProductNotFoundException("Product not found with this id");
        }
    }

    // add new product
    public String addProduct(Product product) {
        if (productDao.existsByProductName(product.getProductName())) {
            throw new GenericeException("Product already present with same name");
        }
        productDao.save(product);
        return "Product added successfully";
    }

    // update product details
    public Product updateProduct(Product product) {
        Optional<Product> optionalProduct = productDao.findById(product.getId());
        Product updatedProduct = optionalProduct.get();
        if(updatedProduct == null) throw new ProductNotFoundException("No product found with this id");
        updatedProduct.setProductName(product.getProductName());
        updatedProduct.setProductPrice(product.getProductPrice());
        productDao.save(updatedProduct);
        return updatedProduct;
    }

    // delete a product
    public String deleteProduct(Long id) {
        if (!productDao.existsById(id)) {
           throw new ProductNotFoundException("Product already not found");
        }
        productDao.deleteById(id);
        return "Product deleted successfully";
    }


    // get products for order
    public List<Product> getProductsForOrder(List<Long> productIds) {
        List<Product> products = null;
        try {
            products = productDao.findByIdIn(productIds);
        }
        catch(Exception ex){
            throw new ProductNotFoundException("product not found from the given list or wrong product ids");
        }
        if(products.isEmpty()){
            logger.warn("Not Products found");
            throw new ProductNotFoundException("No Products not found");
        }
        if(products.size() < productIds.size()) throw new ProductNotFoundException(" Not all products found");
        logger.info("Retrieved {} products " + products.size());
        return products;
    }

}