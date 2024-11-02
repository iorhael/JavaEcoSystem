package com.senla.environment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Water {
    private Integer amount;

    public void increaseAmount(int amount) {
        this.amount += amount;
    }

    public String toSaveFormat(){
        return "water " + amount;
    }
}
