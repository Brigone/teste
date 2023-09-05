package br.com.stoom.store.model;

import br.com.stoom.store.Dto.PriceDTO;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_sequence")
    @SequenceGenerator(name = "price_sequence", sequenceName = "PRICE_SEQ")
    @Column(name = "id")
    private Long id;

    private double price;

    private double discount;

    private double finalPrice;

    public double getFinalPrice() {
        if(this.discount > this.price){
            this.finalPrice = price;
        }else{
            this.finalPrice = this.price - this.discount;
        }
        return this.finalPrice;
    }

    public static Price toModel(PriceDTO dto){
        Price price = new Price();
        price.setPrice(dto.getPrice());
        price.setDiscount(dto.getDiscount());
        price.setFinalPrice(dto.getFinalPrice());
        return price;
    }
}
