package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Product;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface IProductBO {

    List<Product> findAll();

    Product save(Product product);

    void deleteProductById(Long id);

    Product findById(Long id);

}
