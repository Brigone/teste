package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.Dto.ProductDTO;
import br.com.stoom.store.exceptions.BrandDoesNotExitException;
import br.com.stoom.store.exceptions.CategoryDoesNotExitException;
import br.com.stoom.store.exceptions.ProductDoesNotExitException;

import java.util.List;

public interface IProductBO {

    List<ProductDTO> findAll();

    ProductDTO save(ProductDTO product) throws CategoryDoesNotExitException, BrandDoesNotExitException;

    void deleteProductById(Long id);

    ProductDTO findById(Long id);

    ProductDTO alterProduct(ProductDTO product, long id) throws ProductDoesNotExitException, BrandDoesNotExitException, CategoryDoesNotExitException;

    List<ProductDTO> findProductsByCategory(long id);

    List<ProductDTO> findProductsByBrand(long id);

    List<ProductDTO> findAllDisable();

    ProductDTO reActivateById(long id) throws ProductDoesNotExitException;
}
