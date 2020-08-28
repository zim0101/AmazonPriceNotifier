package com.amazon_price_notifier.product;


import java.util.Optional;
import java.util.Set;

/**
 * ProductDao Interface
 */
public interface ProductDao {

    /**
     * Query for product by id and return product object if found or return
     * Optional.empty()
     *
     * @param id Product id for searching product.
     * @return Optional.empty() or Optional< Product >
     */
    Optional<Product> findOne(int id);

    /**
     * Query for all products and return set of product object if table not
     * empty  or return Optional.empty()
     *
     * @return Optional.empty() or Optional< Product >
     */
    Optional<Set<Product>> findAll();

    /**
     * Execute insert query and returns true if product is created and false
     * if product is not created
     *
     * @param product Product object
     * @return boolean
     */
    boolean create(Product product);

    /**
     * Execute update query and returns true if product is created and false
     * if product is not created
     *
     * @param product Product object
     * @return boolean
     */
    boolean update(Product product);
}
