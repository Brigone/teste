package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Category;

import java.util.List;

public interface IPriceBO {

    List<Category> findAll();

    Category save(Category category);

    void deleteCategoryById(Long id);

    Category findCategoryById(Long id);

}
