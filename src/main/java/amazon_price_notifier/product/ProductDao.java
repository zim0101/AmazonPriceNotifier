package amazon_price_notifier.product;

import java.util.Optional;
import java.util.Set;

public interface ProductDao {

    Optional<Set<Product>> findOne(int id);
    Optional<Set<Product>> findAll();
    Optional<Set<Product>> create(Product product);
}
