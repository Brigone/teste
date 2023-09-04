package br.com.stoom.store.controller;

import br.com.stoom.store.Dto.BrandDTO;
import br.com.stoom.store.business.interfaces.IBrandBO;
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

}
