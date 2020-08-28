package com.amazon_price_notifier.product;

import lombok.Data;

/**
 * Product entity contains all columns as properties
 */
@Data
public class Product {
    /**
     * Product id
     */
    public int id;
    /**
     * Product name
     */
    public String name;
    /**
     * Product price
     */
    public double price;
    /**
     * Vendor name. For example: Amazon
     */
    public String vendor;
    /**
     * Product url from the vendor
     */
    public String url;
    /**
     * Id attribute from html of the price dom
     */
    public String priceSelector;
    /**
     * Id attribute from html of the product name dom
     */
    public String nameSelector;

    /**
     * @param id Product id
     * @param name Product name
     * @param price Product price
     * @param vendor Product vendor
     * @param url Product url
     * @param priceSelector Product price selector
     * @param nameSelector Product name selector
     */
    public Product(int id, String name, double price, String vendor, String url, String priceSelector,
                   String nameSelector) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.vendor = vendor;
        this.url = url;
        this.priceSelector = priceSelector;
        this.nameSelector = nameSelector;
    }

    /**
     * @param name Product name
     * @param price Product price
     * @param vendor Product vendor
     * @param url Product url
     * @param priceSelector Product price selector
     * @param nameSelector Product name selector
     */
    public Product(String name, double price, String vendor, String url, String priceSelector,
                   String nameSelector) {
        this.name = name;
        this.price = price;
        this.vendor = vendor;
        this.url = url;
        this.priceSelector = priceSelector;
        this.nameSelector = nameSelector;
    }
}
