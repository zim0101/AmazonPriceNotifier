package amazon_price_notifier.product;

import lombok.Data;

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
    public float price;
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

    public Product(int id, String name, float price, String vendor,
                   String url, String priceSelector, String nameSelector) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.vendor = vendor;
        this.url = url;
        this.priceSelector = priceSelector;
        this.nameSelector = nameSelector;
    }
}
