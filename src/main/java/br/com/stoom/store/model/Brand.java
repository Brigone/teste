package br.com.stoom.store.model;

import br.com.stoom.store.Dto.BrandDTO;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_sequence")
    @SequenceGenerator(name = "brand_sequence", sequenceName = "BRAND_SEQ")
    @Column(name = "id")
    private Long id;
    private String name;

    @CreatedDate
    @Column(updatable = false)
    protected Date created;

    @LastModifiedDate
    protected Date modified;

    @NotNull
    protected Boolean active = true;

    @NotNull
    protected Boolean deleted = false;

    public static Brand toModel(BrandDTO dto){
        Brand brand = new Brand();
        if(dto.getId() != null) brand.setId(dto.getId());
        brand.setName(dto.getName());
        return brand;
    }


    @PrePersist
    public void prePersist() {
        this.created = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

}
