package br.com.stoom.store.Dto;

import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Price;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceDTO {

    private double price;

    private double discount;

    private double finalPrice;


    public static PriceDTO toDto(Price price) {
        return PriceDTO.builder()
                .price(price.getPrice())
                .discount(price.getDiscount())
                .finalPrice(price.getFinalPrice())
                .build();
    }

}
