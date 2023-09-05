package br.com.stoom.store;

import br.com.stoom.store.Dto.ProductDTO;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Price;
import br.com.stoom.store.model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class Builder {

    public static List<Product> getListProductEmpty(){
        return Collections.emptyList();
    }
    public static List<Product> getListProduct(){
        return Arrays.asList(Builder.getProduct(), Builder.getProduct(), Builder.getProduct());
    }

    public static Product getProduct(){
        Product product = new Product();
        product.setName("Product 1");
        product.setSku("123456");
        product.setPrice(Builder.getPrice());
        product.setCategories(Set.of(Builder.getCategory()));
        product.setBrand(Builder.getBrand());
        return product;
    }

    public static Product getProduct2(){
        Product product = new Product();
        product.setName("Product 1");
        product.setSku("123456");
        product.setPrice(Builder.getPrice());
        product.setCategories(Builder.getSetCategories());
        product.setBrand(Builder.getBrand());
        return product;
    }

    public static Price getPrice(){
        Price price = new Price();
        price.setPrice(100);
        price.setDiscount(50);
        return price;
    }

    public static Category getCategory(){
        Category category = new Category();
        category.setId(1L);
        category.setName("Category Name");
        category.setType("Category Type");
        return category;
    }

    public static Set<Category> getSetCategories(){
        Category category1 = new Category();
        category1.setName("Category 1 Name");
        category1.setType("Category 1 Type");

        Category category2 = new Category();
        category2.setName("Category 2 Name");
        category2.setType("Category 2 Type");

        Category category3 = new Category();
        category3.setName("Category 3 Name");
        category3.setType("Category 3 Type");
        return new HashSet<>(Arrays.asList(category1, category2, category3, category1, category1, category3, category1, category2));
    }

    public static Brand getBrand(){
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Brand Name");
        return brand;
    }

    public static List<ProductDTO> getListProductDTO(){
        List<Product> listProduct = Builder.getListProduct();
        return listProduct.stream().map(r -> ProductDTO.toDto(r)).collect(Collectors.toList());
    }

    public static ProductDTO getProductDTO(){
        Product product = Builder.getProduct();
        return ProductDTO.toDto(product);
    }

}
