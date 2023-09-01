package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.model.Product;
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
    private ProductBO productService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> p = productService.findAll();
        if(!p.isEmpty())
            return new ResponseEntity<>(p, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/", produces="application/json", consumes="application/json")
    public ResponseEntity save(@RequestBody Product product) {
        Product p = productService.save(product);
        if(p != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping(value = "/{id}", produces="application/json")
    public ResponseEntity delete(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity edit(@RequestBody Product product, @PathVariable Long id){
        if(product != null && id != null){
            product.setId(id);
            Product newVersionOfProduct =  productService.alterProduct(product);
            return new ResponseEntity(newVersionOfProduct,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
