package br.com.stoom.store.business;

import br.com.stoom.store.Dto.CategoryDTO;
import br.com.stoom.store.Dto.ProductDTO;
import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.exceptions.BrandDoesNotExitException;
import br.com.stoom.store.exceptions.CategoryDoesNotExitException;
import br.com.stoom.store.exceptions.ProductDoesNotExitException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Price;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductBO implements IProductBO {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public List<ProductDTO> findAll(){
        List<Product> products = repository.findByActiveTrue();
        if(products != null){
            List<ProductDTO> productsDto = products.stream().map(r -> ProductDTO.toDto(r)).collect(Collectors.toList());
            return productsDto;
        }
        return Collections.emptyList();
    }

    @Override
    public ProductDTO save(ProductDTO product) throws CategoryDoesNotExitException, BrandDoesNotExitException {
        Product productModel = new Product();
        Set<Category> categories = this.verifyCategories(product);
        productModel.setCategories(categories);
        Brand brand = this.verifyBrand(product);
        productModel.setBrand(brand);
        productModel.setPrice(this.verifyPrice(product));
        productModel.setSku(product.getSku());
        productModel.setName(product.getName());
        Product savedProduct = this.repository.save(productModel);
        return ProductDTO.toDto(savedProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = this.repository.findById(id).orElse(null);
        if(product != null){
            product.setDeleted(true);
            product.setActive(false);
            this.repository.save(product);
        }
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = this.repository.findById(id).orElse(null);
        if(product != null){
            return ProductDTO.toDto(product);
        }
        return null;
    }

    public ProductDTO alterProduct(ProductDTO product, long id) throws ProductDoesNotExitException, BrandDoesNotExitException, CategoryDoesNotExitException {
        Product productFound = this.repository.findById(id).orElse(null);
        if(product != null && productFound != null){
            Set<Category> category = this.verifyCategories(product);
            productFound.setCategories(category);
            Brand brand = this.verifyBrand(product);
            productFound.setBrand(brand);
            productFound.setSku(product.getSku());
            productFound.setName(product.getName());
            productFound.setPrice(this.verifyPrice(product));
            Product productModel = this.repository.save(productFound);
            return ProductDTO.toDto(productModel);
        }
        throw new ProductDoesNotExitException();
    }

    @Override
    public List<ProductDTO> findProductsByCategory(long id) {
        List<Product> products = this.repository.findByCategories_id(id);
        if(products != null){
            List<ProductDTO> dtos = products.stream().parallel().map(r -> ProductDTO.toDto(r)).collect(Collectors.toList());
            return dtos;
        }
        return Collections.emptyList();
    }

    @Override
    public List<ProductDTO> findProductsByBrand(long id) {
        List<Product> products = this.repository.findByBrand_id(id);
        if(products != null){
            List<ProductDTO> dtos = products.stream().parallel().map(r -> ProductDTO.toDto(r)).collect(Collectors.toList());
            return dtos;
        }
        return Collections.emptyList();
    }



    @Override
    public List<ProductDTO> findAllDisable() {
        List<Product> disables = this.repository.findByActiveIsFalse();
        List<ProductDTO> products = disables.stream().parallel().map(r -> ProductDTO.toDto(r)).collect(Collectors.toList());
        return products;
    }

    @Override
    public ProductDTO reActivateById(long id) throws ProductDoesNotExitException {
        Product product = this.repository.findById(id).orElse(null);
        if(product != null){
            product.setActive(true);
            product.setDeleted(false);
            this.repository.save(product);
            return ProductDTO.toDto(product);
        }
        throw new ProductDoesNotExitException();
    }

    private Price verifyPrice(ProductDTO product){
        return product.getPrice() != null ? Price.toModel(product.getPrice()) : new Price();
    }

    private Brand verifyBrand(ProductDTO product) throws BrandDoesNotExitException{
        if(product.getBrand() != null && product.getBrand().getId() != null){
            Brand brand = brandRepository.findById(product.getBrand().getId()).orElse(null);
            if(brand == null) throw new BrandDoesNotExitException();
            return brand;
        }
        throw new BrandDoesNotExitException();
    }

    private Set<Category> verifyCategories(ProductDTO product) throws CategoryDoesNotExitException {
        if(product.getCategories() != null){
            Set<Category> categories = new HashSet<>();
            for (CategoryDTO category: product.getCategories()) {
                Category foundCategory = categoryRepository.findById(category.getId()).orElse(null);
                if(foundCategory == null) throw new CategoryDoesNotExitException();
                categories.add(foundCategory);
            }
            return categories;
        }
        throw new CategoryDoesNotExitException();
    }


}
