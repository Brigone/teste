package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.Dto.BrandDTO;
import br.com.stoom.store.Dto.CategoryDTO;
import br.com.stoom.store.exceptions.BrandDoesNotExitException;

import java.util.List;

public interface IBrandBO {

    List<BrandDTO> findAll();

    BrandDTO save(BrandDTO brand);

    void deleteBrandById(Long id);

    BrandDTO findBrandById(Long id);

    BrandDTO alterBrand(BrandDTO brand, long id) throws BrandDoesNotExitException;
}
