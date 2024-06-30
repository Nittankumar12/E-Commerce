package com.nittan.e_commerce.service;

import com.nittan.e_commerce.dao.ProductDao;
import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.exception.GenericeException;
import com.nittan.e_commerce.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public List<Product> getAllProducts() {
        List<Product> products =  productDao.findAll();
        if(products.isEmpty()) throw new ProductNotFoundException("No products found");
        return products;
    }

    public Product getProductById(Long id) {
        Product product =  productDao.findById(id).get();
        if(product == null) throw new ProductNotFoundException("No product found");
        return product;
    }

    public String addProduct(Product product) {
        if (productDao.existsByProductName(product.getProductName())) {
            throw new GenericeException("Product already present with same name");
        }
        productDao.save(product);
        return "Product added successfully";
    }

    public Product updateProduct(Product product) {
        Optional<Product> optionalProduct = productDao.findById(product.getId());
        Product updatedProduct = optionalProduct.get();
        if(updatedProduct == null) throw new ProductNotFoundException("No product found with this id");
        updatedProduct.setProductName(product.getProductName());
        updatedProduct.setProductPrice(product.getProductPrice());
        productDao.save(updatedProduct);
        return updatedProduct;
    }

    public String deleteProduct(Long id) {
        if (!productDao.existsById(id)) {
           throw new ProductNotFoundException("Product already not found");
        }
        productDao.deleteById(id);
        return "Product deleted successfully";
    }

    public List<Product> getProductsForOrder(List<Long> productIds) {
        List<Product> products =  productDao.findByIdIn(productIds);
        if(products.isEmpty()) throw new ProductNotFoundException("Products not found");
        return products;
    }

}













//package com.nittan.e_commerce.service;
//
//import com.nittan.e_commerce.dao.ProductDao;
//import com.nittan.e_commerce.entity.Product;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ProductService {
//
//    @Autowired
//    ProductDao productDao;
//
//    public List<Product> getAllProducts() {
//        return productDao.findAll();
//    }
//
//
//    public Product getProductById(Long id) {
//        Optional<Product> product = productDao.findById(id);
//        return product.orElse(null);
//    }
//
//    public String addProduct(Product product) {
//        if(productDao.existsByProductName(product.getProductName())) return "product already exists with same name";
//        productDao.save(product);
//        return "Product added Successfully";
//    }
//
//
//    public String updateProduct(Product product) {
//        Optional<Product> optionalProduct = productDao.findById(product.getId());
//        if(optionalProduct.isEmpty()) return "product not exists";
//        Product updatedProduct = optionalProduct.get();
//        updatedProduct.setProductName(product.getProductName());
//        updatedProduct.setProductPrice(product.getProductPrice());
//        updatedProduct.setId(product.getId());
//        productDao.save(updatedProduct);
//        return "product updated";
//    }
//
//    public String deleteProduct(Long id) {
//        if(!productDao.existsById(id)) return "product not exists";
//        productDao.deleteById(id);
//        return "product deleted";
//    }
//
//    public List<Product> getProductsForOrder(List<Long> productIds) {
//        return productDao.findByIdIn(productIds);
//    }
//
//    public List<Product> getProductsFromIds(List<Long> productIds){
//
////        List<ProductWrapper> productWrappers = new ArrayList<>();
//        List<Product> products = new ArrayList<>();
//
//        for(Long id: productIds){
//            products.add(productDao.findById(id).get());
//        }
//        List<Product> response = new ArrayList<>();
//        for(Product product: products){
//            Product product1 = new Product();
//            product1.setProductPrice(product.getProductPrice());
//            product1.setProductName(product.getProductName());
//            product1.setId(product.getId());
//            response.add(product1);
//        }
//        return response;
//    }
//}
