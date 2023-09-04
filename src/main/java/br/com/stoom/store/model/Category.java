package br.com.stoom.store.model;

import br.com.stoom.store.Dto.CategoryDTO;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="categories")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE uuid=?")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "CATEGORY_SEQ")
    @Column(name = "id")
    private Long id;

    private String name;

    private String type;


    protected Date created;

    protected Date modified;

    @NotNull
    protected boolean active = true;

    @NotNull
    protected boolean deleted = false;

    public static Category toModel(CategoryDTO dto){
        Category category = new Category();
        if(dto.getId() != null) category.setId(dto.getId());
        category.setName(dto.getName());
        if(dto.getType() != null) category.setType(dto.getType());
        return category;
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
