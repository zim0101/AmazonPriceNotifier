package com.amazon_price_notifier.product;


import com.amazon_price_notifier.database.DBConfig;
import com.amazon_price_notifier.database.DatabaseConnector;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public abstract class ProductRepository implements ProductDao {

    public Product buildProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getFloat("price"),
                resultSet.getString("vendor"),
                resultSet.getString("url"),
                resultSet.getString("priceSelector"),
                resultSet.getString("nameSelector")
        );
    }

    public Optional<Set<Product>> buildSetOfProduct(ResultSet resultSet) throws SQLException {
        Set<Product> productsList = new LinkedHashSet<>();
        if (resultSet.next()) {
            while (resultSet.next()) {
                Product product = buildProduct(resultSet);
                productsList.add(product);
            }

            return Optional.of(productsList);
        }

        return Optional.empty();
    }

    public Optional<Set<Product>> findOne(int id) {
        try (Connection connection = new DatabaseConnector(DBConfig.defaultDatabase()).connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from products where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return buildSetOfProduct(resultSet);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Set<Product>> findAll() {

        try (Connection connection = new DatabaseConnector(DBConfig.defaultDatabase()).connection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select * from products where id = ?");

            return buildSetOfProduct(resultSet);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean create(Product product) {
        int rowsAffect = 0;
        String query = "insert into products " +
                "(name, price, vendor, url, priceSelector, nameSelector) " +
                "values (?, ?, ?, ?, ?, ?);";

        try (Connection connection = new DatabaseConnector(DBConfig.defaultDatabase()).connection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.name);
            statement.setFloat(2, product.price);
            statement.setString(3, product.vendor);
            statement.setString(4, product.url);
            statement.setString(5, product.priceSelector);
            statement.setString(6, product.nameSelector);
            rowsAffect = statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return rowsAffect > 0;
    }
}
