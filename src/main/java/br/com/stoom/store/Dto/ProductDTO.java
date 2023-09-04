package br.com.stoom.store.Dto;

import br.com.stoom.store.model.Price;
import br.com.stoom.store.model.Product;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    private String sku;

    private String name;

    private boolean active;

    private Set<CategoryDTO> categories;

    private PriceDTO price;

    private BrandDTO brand;

    public static ProductDTO toDto(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .active(product.isActive())
                .categories(product.getCategories().stream().map(r -> CategoryDTO.toDto(r)).collect(Collectors.toSet()))
                .price(PriceDTO.toDto(product.getPrice()))
                .brand(BrandDTO.toDto(product.getBrand()))
                .build();
    }
}