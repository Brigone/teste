package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.Dto.BrandDTO;

import java.util.List;

public interface IBrandBO {

    List<BrandDTO> findAll();

    BrandDTO save(BrandDTO brand);

    void deleteBrandById(Long id);

    BrandDTO findBrandById(Long id);

}
