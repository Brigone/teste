package br.com.stoom.store.model;

import br.com.stoom.store.Dto.ProductDTO;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="products")
@Where(clause = "deleted=false")
//@SQLDelete(sql = "UPDATE products SET deleted = true WHERE uuid=?")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "PRODUCT_SEQ")
    @Column(name = "id_products")
    private Long id;

    @NotNull
    @NotBlank(message = "sku it's a mandatory!")
    @Column(name = "sku")
    private String sku;

    private String name;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "product_category", joinColumns = {@JoinColumn(name = "product_ID", insertable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "category_ID")})
    private Set<Category> categories;
    @OneToOne(cascade=CascadeType.ALL)
    private Price price;
    @OneToOne(cascade=CascadeType.ALL)
    private Brand brand;


    @CreatedDate
    @Column(updatable = false)
    protected Date created;

    @LastModifiedDate
    protected Date modified;

    @NotNull
    protected boolean active = true;

    @NotNull
    protected boolean deleted = false;


    public static Product toModel(ProductDTO dto){
        Product product = new Product();


        return product;
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