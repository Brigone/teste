package br.com.stoom.store.Dto;

import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandDTO {
    private Long id;
    private String name;

    private boolean active;

    public static BrandDTO toDto(Brand brand) {
        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .active(brand.getActive())
                .build();
    }


}
