package br.com.stoom.store.controller;

import br.com.stoom.store.Dto.BrandDTO;
import br.com.stoom.store.Dto.CategoryDTO;
import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.exceptions.BrandDoesNotExitException;
import br.com.stoom.store.exceptions.CategoryDoesNotExitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private IBrandBO service;


    @GetMapping(value = "/")
    public ResponseEntity<List<BrandDTO>> findAll() {
        List<BrandDTO> brand = service.findAll();
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BrandDTO> findById( @PathVariable Long id) {
        BrandDTO brand = service.findBrandById(id);
        if(brand != null) {
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/", produces="application/json", consumes="application/json")
    public ResponseEntity save(@RequestBody BrandDTO dto) {
        BrandDTO brand = service.save(dto);
        if(brand != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping(value = "/{id}", produces="application/json")
    public ResponseEntity delete(@PathVariable String id) {
        service.deleteBrandById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity edit(@RequestBody BrandDTO brand, @PathVariable String id){
        if(brand != null && id != null){
            try {
                BrandDTO response = service.alterBrand(brand, Long.parseLong(id));
                return new ResponseEntity(response,HttpStatus.OK);
            } catch (BrandDoesNotExitException e) {
                return new ResponseEntity(e.getMessage(),HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
