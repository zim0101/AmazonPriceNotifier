package com.amazon_price_notifier.product;


import com.amazon_price_notifier.database.DBConfig;
import com.amazon_price_notifier.database.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * ProductRepository class implements ProductDao interface.
 * Returns Product entity, set of entities or boolean to determine success of
 * execution.
 */
public class ProductRepository implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    /**
     * Build single product object from result
     *
     * @param resultSet ResultSet
     * @return Product entity
     * @throws SQLException SQL exception
     */
    public Product buildProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getFloat("price"),
                resultSet.getString("site_name"),
                resultSet.getString("url"),
                resultSet.getString("price_selector"),
                resultSet.getString("name_selector")
        );
    }

    /**
     * Returns optional set of product objects
     * @param resultSet ResultSet
     * @return Optional Product set
     * @throws SQLException SQLException
     */
    public Optional<Set<Product>> buildSetOfProduct(ResultSet resultSet) throws SQLException {
        Set<Product> productsList = new LinkedHashSet<>();
        while (resultSet.next()) {
            Product product = buildProduct(resultSet);
            productsList.add(product);
        }

        return Optional.of(productsList);
    }

    /**
     * Query for product by id and return product object if found or return
     * Optional.empty()
     *
     * @param id Product id for searching product.
     * @return Optional.empty() or Optional< Product >
     */
    public Optional<Product> findOne(int id) {
        try (Connection connection = new DBConnector(DBConfig.primary()).connection()) {
            ResultSet resultSet = ProductQuery.findOne(connection, id).executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildProduct(resultSet));
            }
        } catch (SQLException | ClassNotFoundException exception) {
            logger.error("Product Query Exception (Exception occurred while finding a product): "
                    , exception);
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Query for all products and return set of product object if table not
     * empty  or return Optional.empty()
     *
     * @return Optional.empty() or Optional< Product >
     */
    public Optional<Set<Product>> findAll() {
        try (Connection connection = new DBConnector(DBConfig.primary()).connection()) {
            ResultSet resultSet = ProductQuery.findAll(connection).executeQuery();

            return buildSetOfProduct(resultSet);
        } catch (SQLException | ClassNotFoundException exception) {
            logger.error("Product Query Exception (Exception occurred while finding all products)" +
                    ": ", exception);
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Execute insert query and returns true if product is created and false
     * if product is not created
     *
     * @param product Product object
     * @return boolean
     */
    public boolean create(Product product) {
        int rowsAffect = 0;

        try (Connection connection = new DBConnector(DBConfig.primary()).connection()) {
            rowsAffect = ProductQuery.create(product, connection).executeUpdate();
        } catch (SQLException | ClassNotFoundException exception) {
            logger.error("Product Query Exception (Exception occurred while creating product): ",
                    exception);
            exception.printStackTrace();
        }

        return rowsAffect > 0;
    }

    /**
     * Execute update query and returns true if product is created and false
     * if product is not created
     *
     * @param product Product object
     * @return boolean
     */
    public boolean update(Product product) {
        int rowsAffect = 0;
        try (Connection connection = new DBConnector(DBConfig.primary()).connection()) {

            rowsAffect = ProductQuery.update(product, connection).executeUpdate();
        } catch (SQLException | ClassNotFoundException exception) {
            logger.error("Product Query Exception (Exception occurred while updating a product): ",
                    exception);
            exception.printStackTrace();
        }

        return rowsAffect > 0;
    }
}
