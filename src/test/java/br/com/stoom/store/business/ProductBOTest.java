package br.com.stoom.store.business;

import br.com.stoom.store.Builder;
import br.com.stoom.store.Dto.ProductDTO;
import br.com.stoom.store.exceptions.BrandDoesNotExitException;
import br.com.stoom.store.exceptions.CategoryDoesNotExitException;
import br.com.stoom.store.exceptions.ProductDoesNotExitException;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class ProductBOTest {

    @InjectMocks
    private ProductBO bo;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void when_call_find_all_should_be_ok(){
        Mockito.when(repository.findByActiveTrue()).thenReturn(Builder.getListProduct());
        List<ProductDTO> products = bo.findAll();
        Assert.assertNotNull(products);
        Assert.assertEquals(3, products.size());
    }

    @Test
    public void when_call_find_all_should_be_empty(){
        Mockito.when(repository.findByActiveTrue()).thenReturn(null);
        List<ProductDTO> products = bo.findAll();
        Assert.assertNotNull(products);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void when_call_find_by_id_should_be_ok(){
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(Builder.getProduct()));
        ProductDTO products = bo.findById(anyLong());
        Assert.assertNotNull(products);
    }

    @Test
    public void when_call_find_by_id_should_be_null(){
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        ProductDTO products = bo.findById(anyLong());
        Assert.assertNull(products);
    }

    @Test
    public void when_call_find_product_by_brand_should_be_ok(){
        Mockito.when(repository.findByBrand_id(anyLong())).thenReturn(Builder.getListProduct());
        List<ProductDTO> products = bo.findProductsByBrand(anyLong());
        Assert.assertNotNull(products);
        Assert.assertEquals(3, products.size());
    }

    @Test
    public void when_call_find_product_by_brand_should_be_null(){
        Mockito.when(repository.findByBrand_id(anyLong())).thenReturn(null);
        List<ProductDTO> products = bo.findProductsByBrand(anyLong());
        Assert.assertNotNull(products);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void when_call_find_product_by_category_should_be_ok(){
        Mockito.when(repository.findByCategories_id(anyLong())).thenReturn(Collections.emptyList());
        List<ProductDTO> products = bo.findProductsByCategory(anyLong());
        Assert.assertNotNull(products);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void when_call_find_product_by_category_should_be_null(){
        Mockito.when(repository.findByCategories_id(anyLong())).thenReturn(null);
        List<ProductDTO> products = bo.findProductsByCategory(anyLong());
        Assert.assertNotNull(products);
        Assert.assertEquals(0, products.size());
    }


    @Test
    public void when_call_save_product_should_be_ok() throws CategoryDoesNotExitException, BrandDoesNotExitException {
        Mockito.when(repository.save(any(Product.class))).thenReturn(Builder.getProduct());
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(Builder.getCategory()));
        Mockito.when(brandRepository.findById(anyLong())).thenReturn(Optional.of(Builder.getBrand()));
        ProductDTO products = bo.save(Builder.getProductDTO());
        Assert.assertNotNull(products);
        Assert.assertNotNull(products.getBrand());
        Assert.assertEquals(1, products.getCategories().size());
        Assert.assertEquals( 50, products.getPrice().getFinalPrice(), 0);
    }

    @Test
    public void when_call_save_product_should_be_error_category() throws BrandDoesNotExitException {
        CategoryDoesNotExitException categoryDoesNotExitException = Assert.assertThrows(CategoryDoesNotExitException.class, () -> {
            Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
            ProductDTO products = null;
            products = bo.save(Builder.getProductDTO());
        });
        Assert.assertEquals("This category is empty or does not exist!", categoryDoesNotExitException.getMessage() );
    }

    @Test
    public void when_call_save_product_should_be_error_brand() {
        BrandDoesNotExitException error = Assert.assertThrows(BrandDoesNotExitException.class, () -> {
            Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(Builder.getCategory()));
            Mockito.when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());
            ProductDTO products = bo.save(Builder.getProductDTO());
        });
        Assert.assertEquals("This brand is empty or does not exist!", error.getMessage() );
    }

    @Test
    public void when_call_alter_product_should_be_ok() throws CategoryDoesNotExitException, BrandDoesNotExitException, ProductDoesNotExitException {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(Builder.getProduct()));
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(Builder.getCategory()));
        Mockito.when(brandRepository.findById(anyLong())).thenReturn(Optional.of(Builder.getBrand()));
        Mockito.when(repository.save(any(Product.class))).thenReturn(Builder.getProduct());
        ProductDTO products = bo.alterProduct(Builder.getProductDTO(), anyLong());
        Assert.assertNotNull(products);
        Assert.assertNotNull(products.getBrand());
        Assert.assertEquals(1, products.getCategories().size());
        Assert.assertNotEquals( 50, products.getPrice().getFinalPrice());
    }

    @Test
    public void when_call_alter_product_should_be_error() {
        ProductDoesNotExitException error = Assert.assertThrows(ProductDoesNotExitException.class, () -> {
            ProductDTO products = bo.alterProduct(Builder.getProductDTO(), anyLong());
        });
        Assert.assertEquals( "This Product is empty or does not exist!", error.getMessage());
    }


    @Test
    public void when_call_reactivate_product_should_be() throws ProductDoesNotExitException {
        Mockito.when(this.repository.findById(anyLong())).thenReturn(Optional.of(Builder.getProduct()));
        Mockito.when(this.repository.save(any(Product.class))).thenReturn(Builder.getProduct());
        ProductDTO productDTO = bo.reActivateById(anyLong());
        Assert.assertNotNull(productDTO);


    }

}
