package br.com.stoom.store.controller;

import br.com.stoom.store.Dto.CategoryDTO;
import br.com.stoom.store.Dto.ProductDTO;
import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.exceptions.CategoryDoesNotExitException;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private ICategoryBO service;


    @GetMapping(value = "/all")
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> categories = service.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById( @PathVariable Long id) {
        CategoryDTO category = service.findCategoryById(id);
        if(category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/", produces="application/json", consumes="application/json")
    public ResponseEntity save(@RequestBody CategoryDTO dto) {
        CategoryDTO category = service.save(dto);
        if(category != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}", produces="application/json")
    public ResponseEntity delete(@PathVariable String id) {
        service.deleteCategoryById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/reactivate/{id}", produces="application/json")
    public ResponseEntity reactivate(@PathVariable String id) {
        CategoryDTO categoryDTO = service.reactivateCategory(Long.parseLong(id));
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity edit(@RequestBody CategoryDTO category, @PathVariable String id){
        if(category != null && id != null){
            try {
                CategoryDTO response = service.alterCategory(category, Long.parseLong(id));
                return new ResponseEntity(response,HttpStatus.OK);
            } catch (CategoryDoesNotExitException e) {
                return new ResponseEntity(e.getMessage(),HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
