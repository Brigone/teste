package br.com.stoom.store.Dto;

import br.com.stoom.store.model.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO{

    private Long id;
    private String name;

    private String type;

    private boolean active;


    public static CategoryDTO toDto(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .active(category.isActive())
                .build();
    }
}
