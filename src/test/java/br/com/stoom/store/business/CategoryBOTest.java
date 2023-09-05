package br.com.stoom.store.business;

import br.com.stoom.store.Builder;
import br.com.stoom.store.Dto.CategoryDTO;
import br.com.stoom.store.exceptions.CategoryDoesNotExitException;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
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
public class CategoryBOTest {
    @InjectMocks
    private CategoryBO bo;

    @Mock
    private CategoryRepository repository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void when_call_find_all_should_be_ok(){
        Mockito.when(repository.findByActiveTrue()).thenReturn(Builder.getListCategories());
        List<CategoryDTO> all = bo.findAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(8, all.size());

    }

    @Test
    public void when_call_find_all_should_be_empty(){
        Mockito.when(repository.findByActiveTrue()).thenReturn(Collections.emptyList());
        List<CategoryDTO> all = bo.findAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(0, all.size());

    }

    @Test
    public void when_call_save_should_be_ok(){
        Mockito.when(repository.save(any(Category.class))).thenReturn(Builder.getCategory());
        CategoryDTO dto = bo.save(Builder.getCategoryDTO());
        Assert.assertNotNull(dto);
    }

    @Test
    public void when_call_save_should_be_null(){
        CategoryDTO dto = bo.save(null);
        Assert.assertNull(dto);
    }

    @Test
    public void when_call_find_by_id_should_be_ok(){
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(Builder.getCategory()));
        CategoryDTO all = bo.findCategoryById(anyLong());
        Assert.assertNotNull(all);
    }

    @Test
    public void when_call_alter_should_be_ok() throws CategoryDoesNotExitException {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(Builder.getCategory()));
        CategoryDTO categoryDTO = bo.alterCategory(Builder.getCategoryDTO(), anyLong());
        Assert.assertNotNull(categoryDTO);
    }

    @Test
    public void when_call_alter_should_be_error() throws CategoryDoesNotExitException {
        CategoryDoesNotExitException error = Assert.assertThrows(CategoryDoesNotExitException.class, ()->{
            bo.alterCategory(null, anyLong());
        });
        Assert.assertEquals("This category is empty or does not exist!", error.getMessage());
    }

}
