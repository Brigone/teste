package br.com.stoom.store.business;

import br.com.stoom.store.Builder;
import br.com.stoom.store.Dto.BrandDTO;
import br.com.stoom.store.exceptions.BrandDoesNotExitException;
import br.com.stoom.store.repository.BrandRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class BrandBOTest {
    @InjectMocks
    private BrandBO bo;

    @Mock
    private BrandRepository repository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void when_call_find_all_should_be_ok(){
        Mockito.when(repository.findByActiveTrue()).thenReturn(Builder.getListBrand());
        List<BrandDTO> all = bo.findAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(3, all.size());

    }

    @Test
    public void when_call_find_all_should_be_not_ok(){
        Mockito.when(repository.findByActiveTrue()).thenReturn(null);
        List<BrandDTO> all = bo.findAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(0, all.size());

    }

    @Test
    public void when_call_find_by_id_should_be_ok(){
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(Builder.getBrand()));
        BrandDTO all = bo.findBrandById(anyLong());
        Assert.assertNotNull(all);
    }

    @Test
    public void when_call_save_should_be_ok() throws BrandDoesNotExitException {
        Mockito.when(this.repository.save(any())).thenReturn(Builder.getBrand());
        BrandDTO brandDTO = bo.save(Builder.getProductDTO().getBrand());
        Assert.assertNotNull(brandDTO);
        Assert.assertEquals(Builder.getProductDTO().getBrand().getName(), brandDTO.getName());
    }

    @Test
    public void when_call_save_should_be_not_ok() {
        BrandDTO brandDTO = bo.save(null);
        Assert.assertNull(brandDTO);
    }
    @Test
    public void when_call_alter_should_be_ok() throws BrandDoesNotExitException {
        Mockito.when(this.repository.findById(anyLong())).thenReturn(Optional.of(Builder.getBrand()));
        BrandDTO brandDTO = bo.alterBrand(Builder.getProductDTO().getBrand(), anyLong());
        Assert.assertNotNull(brandDTO);
        Assert.assertEquals(Builder.getProductDTO().getBrand().getName(), brandDTO.getName());
    }

    @Test
    public void when_call_alter_should_be_not_ok(){
        BrandDoesNotExitException error = Assert.assertThrows(BrandDoesNotExitException.class, () -> {
            Mockito.when(this.repository.findById(anyLong())).thenReturn(Optional.empty());
            bo.alterBrand(Builder.getProductDTO().getBrand(), anyLong());
        });
        Assert.assertEquals("This brand is empty or does not exist!", error.getMessage() );
    }


}
