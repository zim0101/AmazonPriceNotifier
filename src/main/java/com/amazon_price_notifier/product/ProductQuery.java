package com.amazon_price_notifier.product;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// TODO: Create BaseQuery class and write base queries there
// TODO: Extend BaseQuery here.
/**
 * Prepares query statement and returns it.
 */
public class ProductQuery {

    /**
     * Prepare statement for finding product by id.
     *
     * @param connection Database connection
     * @param id Product id
     * @return PreparedStatement
     * @throws SQLException SQL Exception
     */
    public static PreparedStatement findOne(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from products where id = ?");
        preparedStatement.setInt(1, id);

        return preparedStatement;
    }

    /**
     * Prepares query statement for finding product by price.
     *
     * @param connection Database connection
     * @param price Product price
     * @return PreparedStatement
     * @throws SQLException SQL Exception
     */
    @SneakyThrows
    public static PreparedStatement findByPrice(Connection connection, double price) {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from products where price = ?");
        preparedStatement.setDouble(1, price);

        return preparedStatement;
    }

    /**
     * Prepares query statement for finding all products in a price range.
     *
     * @param connection Database connection
     * @param upperRange upper range of product price
     * @param lowerRange lower range of product price
     * @return PreparedStatement
     * @throws SQLException SQL Exception
     */
    public static PreparedStatement findByPriceRange(Connection connection, double upperRange,
                                                     double lowerRange) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from products where price >= ? and price <= ?");
        preparedStatement.setDouble(1, lowerRange);
        preparedStatement.setDouble(2, upperRange);

        return preparedStatement;
    }

    /**
     * Prepares query statement for finding all products.
     *
     * @param connection Database connection
     * @return PreparedStatement
     * @throws SQLException SQL Exception
     */
    public static PreparedStatement findAll(Connection connection) throws SQLException{
        return connection.prepareStatement("select * from products");
    }

    /**
     * Prepares query statement for creating a product.
     *
     * @param connection Database connection
     * @return PreparedStatement
     * @throws SQLException SQL Exception
     */
    public static PreparedStatement create(Product product, Connection connection) throws SQLException {
        String query = "insert into products "
                + "(`name`, price, site_name, url, price_selector, name_selector) "
                + "values (?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        return prepareColumnData(product, statement);

    }

    /**
     * Prepares query statement for updating a product.
     *
     * @param connection Database connection
     * @return PreparedStatement
     * @throws SQLException SQL Exception
     */
    public static PreparedStatement update(Product product, Connection connection) throws SQLException {
        String query = "update products set "
                + "`name` = ?, "
                + "price = ?, "
                + "site_name = ?, "
                + "url = ?, "
                + "price_selector = ?, "
                + "name_selector = ? where id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(7, product.id);

        return prepareColumnData(product, statement);

    }


    /**
     * Add prepared statement parameters which is both useful for creating
     * new product or updating all columns of existing product.
     *
     * @param product Product object
     * @param statement PreparedStatement
     * @return PreparedStatement
     * @throws SQLException SQLException
     */
    private static PreparedStatement prepareColumnData(Product product, PreparedStatement statement)
            throws SQLException {

        statement.setString(1, product.name);
        statement.setDouble(2, product.price);
        statement.setString(3, product.vendor);
        statement.setString(4, product.url);
        statement.setString(5, product.priceSelector);
        statement.setString(6, product.nameSelector);

        return statement;
    }
}
