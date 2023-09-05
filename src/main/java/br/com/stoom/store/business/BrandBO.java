package br.com.stoom.store.business;

import br.com.stoom.store.Dto.BrandDTO;
import br.com.stoom.store.Dto.CategoryDTO;
import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.exceptions.BrandDoesNotExitException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandBO implements IBrandBO {

    @Autowired
    BrandRepository repository;

    @Override
    public List<BrandDTO> findAll() {
        List<Brand> brands = this.repository.findByActiveTrue();
        return brands.stream().parallel().map(r -> BrandDTO.toDto(r)).collect(Collectors.toList());
    }

    @Override
    public BrandDTO save(BrandDTO brand) {
        Brand savedBrand = this.repository.save(Brand.toModel(brand));
        if(savedBrand == null) return null;
        return BrandDTO.toDto(savedBrand);
    }

    @Override
    public void deleteBrandById(Long id) {
        Brand brand = this.repository.findById(id).orElse(null);
        if(brand != null){
            brand.setDeleted(true);
            brand.setActive(false);
            this.repository.save(brand);
        }
    }

    @Override
    public BrandDTO findBrandById(Long id) {
        Brand brand = this.repository.findById(id).orElse(null);
        if(brand == null) return null;
        return BrandDTO.toDto(brand);
    }

    @Override
    public BrandDTO alterBrand(BrandDTO dto, long id) throws BrandDoesNotExitException {
        Brand brand = this.repository.findById(id).orElse(null);
        if(brand != null){
            brand.setName(dto.getName());
            this.repository.save(brand);
            return BrandDTO.toDto(brand);
        }
        throw new BrandDoesNotExitException();
    }
}
