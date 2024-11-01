package com.senla.environment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Water {
    private Integer amount;

    public Water(int initialAmount) {
        this.amount = initialAmount;
    }

    public void increaseAmount(int amount) {
        this.amount += amount;
    }

//    public int getAmount() {
//        return amount;
//    }

//    public void setAmount(int amount){
//        this.amount = amount;
//    }
}
