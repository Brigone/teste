package br.com.stoom.store.business;

import br.com.stoom.store.Dto.CategoryDTO;
import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.exceptions.CategoryDoesNotExitException;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryBO implements ICategoryBO {

    @Autowired
    CategoryRepository repository;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = this.repository.findByActiveTrue();
        if(categories != null){
            return categories.stream().parallel().map(r -> CategoryDTO.toDto(r)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public CategoryDTO save(CategoryDTO category) {
        if(category != null){
            Category savedCategory = this.repository.save(Category.toModel(category));
            if(savedCategory != null) {
                return CategoryDTO.toDto(savedCategory);
            }
        }
        return null;
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = this.repository.findById(id).orElse(null);
        if(category != null){
            category.setDeleted(true);
            category.setActive(false);
            this.repository.save(category);
        }
    }

    @Override
    public CategoryDTO findCategoryById(Long id) {
        Category category = this.repository.findById(id).orElse(null);
        if(category != null){
            return CategoryDTO.toDto(category);
        }
        return null;
    }

    @Override
    public CategoryDTO alterCategory(CategoryDTO dto, Long id) throws CategoryDoesNotExitException {
        Category category = this.repository.findById(id).orElse(null);
        if(category != null && dto != null && id != null){
            category.setName(dto.getName());
            category.setType(dto.getType());
            this.repository.save(category);
            return CategoryDTO.toDto(category);
        }
        throw new CategoryDoesNotExitException();
    }

    @Override
    public CategoryDTO reactivateCategory(long id) {
        Category category = this.repository.findById(id).orElse(null);
        if(category != null){
            category.setActive(true);
            category.setDeleted(true);
            return CategoryDTO.toDto(category);
        }
        return null;
    }

}
