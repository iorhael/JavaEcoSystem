package com.senla.plant;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class BerryBush extends Plant {
    public BerryBush(int amount) {
        super(amount, "BerryBush");
    }
}