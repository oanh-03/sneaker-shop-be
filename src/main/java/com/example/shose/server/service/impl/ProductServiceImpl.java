package com.example.shose.server.service.impl;

import com.example.shose.server.dto.request.product.CreateProductRequest;
import com.example.shose.server.dto.request.product.FindProductRequest;
import com.example.shose.server.dto.request.product.UpdateProductRequest;
import com.example.shose.server.dto.request.productdetail.FindProductDetailRequest;
import com.example.shose.server.dto.response.CustomProductRespone;
import com.example.shose.server.dto.response.ProductDetailReponse;
import com.example.shose.server.dto.response.ProductResponse;
import com.example.shose.server.entity.Product;
import com.example.shose.server.infrastructure.constant.Message;
import com.example.shose.server.infrastructure.exception.rest.RestApiException;
import com.example.shose.server.repository.ProductRepository;
import com.example.shose.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Nguyễn Vinh
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<ProductResponse> findAll(FindProductRequest req) {
        return productRepository.getAll(req);
    }

    @Override
    public List<String> fillAllByName(String name) {
        return productRepository.findAllByName(name);
    }

    @Override
    public Product create(CreateProductRequest req) {
        Product checkCode = productRepository.getOneByCode(req.getCode());
        if (checkCode != null) {
            throw new RestApiException(Message.CODE_EXISTS);
        }
        Product add = new Product();
        add.setStatus(req.getStatus());
        add.setName(req.getName());
        add.setCode(req.getCode());
        return productRepository.save(add);
    }

    @Override
    public Product update(UpdateProductRequest req) {
        Optional<Product> optional = productRepository.findById(req.getId());
        if (!optional.isPresent()) {
            throw new RestApiException(Message.NOT_EXISTS);
        }
        Product checkCode = productRepository.getOneByCode(req.getCode());
        if (checkCode != null) {
            throw new RestApiException(Message.CODE_EXISTS);
        }
        Product update = optional.get();
        update.setCode(req.getCode());
        update.setName(req.getName());
        update.setStatus(req.getStatus());
        return productRepository.save(update);
    }

    @Override
    public Boolean delete(String id) {
        Optional<Product> optional = productRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.NOT_EXISTS);
        }
        productRepository.delete(optional.get());
        return true;
    }

    @Override
    public Product getOneById(String id) {
        Optional<Product> optional = productRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.NOT_EXISTS);
        }
        return optional.get();
    }

    @Override
    public List<Product> getProductUse() {
        return productRepository.getProductUse();
    }

    @Override
    public List<ProductDetailReponse> getAllProduct(FindProductDetailRequest req) {
        return productRepository.getAllProduct(req);
    }
}
