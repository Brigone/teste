package br.com.stoom.store.controller;

import br.com.stoom.store.Dto.ProductDTO;
import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.exceptions.BrandDoesNotExitException;
import br.com.stoom.store.exceptions.CategoryDoesNotExitException;
import br.com.stoom.store.exceptions.ProductDoesNotExitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductBO service;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> p = service.findAll();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping(value = "/all/disable")
    public ResponseEntity<List<ProductDTO>> findAllDisable() {
        List<ProductDTO> p = service.findAllDisable();
        return new ResponseEntity<>(p, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById( @PathVariable Long id) {
        ProductDTO p = service.findById(id);
        if(p != null) {
            return new ResponseEntity<>(p, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/category/{id}")
    public ResponseEntity<List<ProductDTO>> findProductByCategory( @PathVariable Long id) {
        List<ProductDTO> products = service.findProductsByCategory(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping(value = "/brand/{id}")
    public ResponseEntity<List<ProductDTO>> findProductByBrand( @PathVariable Long id) {
        List<ProductDTO> products = service.findProductsByBrand(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping(value = "/", produces="application/json", consumes="application/json")
    public ResponseEntity save(@RequestBody ProductDTO product) {
        ProductDTO p = null;
        try {
            p = service.save(product);
            if(p != null)
                return new ResponseEntity<>(HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CategoryDoesNotExitException | BrandDoesNotExitException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/{id}", produces="application/json")
    public ResponseEntity delete(@PathVariable String id) {
        service.deleteProductById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/reactivate/{id}", produces="application/json")
    public ResponseEntity reactivate(@PathVariable String id) {
        try {
            ProductDTO productDTO = this.service.reActivateById(Long.parseLong(id));
            return new ResponseEntity(productDTO,HttpStatus.OK);
        } catch (ProductDoesNotExitException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity edit(@RequestBody ProductDTO product, @PathVariable String id){
        if(product != null && id != null){
            ProductDTO response = null;
            try {
                response = service.alterProduct(product, Long.parseLong(id));
                return new ResponseEntity(response,HttpStatus.OK);
            } catch (ProductDoesNotExitException | BrandDoesNotExitException | CategoryDoesNotExitException e) {
                return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
