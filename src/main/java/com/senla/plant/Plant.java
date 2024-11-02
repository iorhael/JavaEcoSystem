package com.senla.plant;


import lombok.*;

@Getter
@Setter
public class Plant {
    protected Integer amount;
    protected String type;

    public Plant(){}

    public Plant(int amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public void decreaseAmount(int amount) {
        this.amount -= amount;
        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    public String toSaveFormat(){
        return "plant " + this.getType() + " " + amount;
    }
}
