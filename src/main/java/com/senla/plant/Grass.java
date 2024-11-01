package com.senla.plant;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Grass extends Plant {
    public Grass(int amount) {
        super(amount, "Grass");
    }
}
