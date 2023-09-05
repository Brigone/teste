package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.Dto.CategoryDTO;
import br.com.stoom.store.exceptions.CategoryDoesNotExitException;
import br.com.stoom.store.model.Category;

import java.util.List;

public interface ICategoryBO {

    List<CategoryDTO> findAll();

    CategoryDTO save(CategoryDTO category);

    void deleteCategoryById(Long id);

    CategoryDTO findCategoryById(Long id);

    CategoryDTO alterCategory(CategoryDTO dto, Long id) throws CategoryDoesNotExitException;

    CategoryDTO reactivateCategory(long id);
}
