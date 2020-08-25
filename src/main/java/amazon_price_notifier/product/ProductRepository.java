package amazon_price_notifier.product;


import amazon_price_notifier.database.DatabaseConnector;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public abstract class ProductRepository extends DatabaseConnector implements ProductDao {

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

    public Optional<Set<Product>> buildSetOfProduct(ResultSet resultSet)
            throws SQLException {
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

    public Optional<Set<Product>> boilerplate(Function<Connection,
                                              Optional<Set<Product>>> function) {
        try (Connection connection = connection()) {
            return function.apply(connection);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Set<Product>> findOne(int id) {
        return boilerplate((connection) -> executeFindOneQuery(id, connection));
    }

    public Optional<Set<Product>> findAll() {
        return boilerplate(this::executeFindAllQuery);
    }

    public Optional<Set<Product>> create(Product product) {
        return boilerplate((connection) -> executeInsertQuery(product, connection));
    }

    public Optional<Set<Product>> executeFindOneQuery(int id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from products where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return buildSetOfProduct(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Set<Product>> executeFindAllQuery(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "select * from products where id = ?");

            return buildSetOfProduct(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Set<Product>> executeInsertQuery(Product product, Connection connection) {
        String query = "insert into products " +
                "(name, price, vendor, url, priceSelector, nameSelector) " +
                "values (?, ?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, product.name);
            statement.setFloat(2, product.price);
            statement.setString(3, product.vendor);
            statement.setString(4, product.url);
            statement.setString(5, product.priceSelector);
            statement.setString(6, product.nameSelector);

            ResultSet result = statement.getGeneratedKeys();

            if(result.next()){
                return findOne(result.getInt(1));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }
}
