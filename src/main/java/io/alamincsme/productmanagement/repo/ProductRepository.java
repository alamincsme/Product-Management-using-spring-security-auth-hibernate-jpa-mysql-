package io.alamincsme.productmanagement.repo;

import io.alamincsme.productmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
