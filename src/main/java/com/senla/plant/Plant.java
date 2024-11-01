package com.senla.plant;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public abstract class Plant {
    protected Integer amount;
    protected String type;

    public Plant(int amount, String type) {
        this.amount = amount;
        this.type = type;
    }

//    public String getType() {
//        return type;
//    }
//
//    public int getAmount() {
//        return amount;
//    }

    public void decreaseAmount(int amount) {
        this.amount -= amount;
        if (this.amount < 0) {
            this.amount = 0; // Plant amount cannot be negative
        }
    }
}
