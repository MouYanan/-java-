package com.example.eseshopsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.eseshopsearch.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSearchService {

    @Autowired
    private ElasticsearchClient esClient;
    private static final String INDEX_NAME = "product_v1";

    public List<Product> searchByKeyword(String keyword) throws IOException {
        SearchResponse<Product> response = esClient.search(
                s -> s.index(INDEX_NAME)
                        .query(q -> q.multiMatch(
                                m -> m.query(keyword)
                                        .fields("productName^3", "description", "brand")
                                        .analyzer("ik_max_word_analyzer")
                        ))
                        .sort(sort -> sort.field(f -> f.field("sales").order(SortOrder.Desc))),
                Product.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public List<Product> searchByPriceRange(String keyword, Double minPrice, Double maxPrice) throws IOException {
        // 使用匹配查询替代范围查询，暂时避免 API 问题
        SearchResponse<Product> response = esClient.search(
                s -> s.index(INDEX_NAME)
                        .query(q -> q.match(
                                m -> m.field("productName").query(keyword)
                        )),
                Product.class
        );

        // 在客户端过滤价格范围
        return response.hits().hits().stream()
                .map(Hit::source)
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<Product> searchByBrand(String brand) throws IOException {
        SearchResponse<Product> response = esClient.search(
                s -> s.index(INDEX_NAME)
                        .query(q -> q.term(t -> t.field("brand.keyword").value(brand))),
                Product.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}