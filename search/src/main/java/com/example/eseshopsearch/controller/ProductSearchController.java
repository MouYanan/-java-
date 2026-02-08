package com.example.eseshopsearch.controller;

import com.example.eseshopsearch.entity.Product;
import com.example.eseshopsearch.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductSearchController {

    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping("/search/keyword")
    public List<Product> searchByKeyword(@RequestParam String keyword) throws IOException {
        return productSearchService.searchByKeyword(keyword);
    }

    @GetMapping("/search/price")
    public List<Product> searchByPriceRange(
            @RequestParam String keyword,
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice
    ) throws IOException {
        return productSearchService.searchByPriceRange(keyword, minPrice, maxPrice);
    }

    @GetMapping("/search/brand")
    public List<Product> searchByBrand(@RequestParam String brand) throws IOException {
        return productSearchService.searchByBrand(brand);
    }
}