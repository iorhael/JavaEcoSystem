package com.senla.animal;


import com.senla.AliveStatus;
import com.senla.environment.Water;
import com.senla.plant.Plant;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Herbivore extends Animal {
    private Integer plantConsumption;

    public Herbivore(){}

    public Herbivore(String name, int defense) {
        super(name, defense);
        this.plantConsumption = 1; // Each herbivore consumes one unit of plant
    }

    public Herbivore(Integer plantConsumption, Double health, Integer defense, Integer daysWithoutWater, Integer MAX_DAYS_WITHOUT_WATER, String name, AliveStatus status, Integer basicWaterConsumption, Integer basicHungerDamage, Boolean isHungry, Boolean isThirsty){
        super(health, defense, daysWithoutWater, MAX_DAYS_WITHOUT_WATER, name, status, basicWaterConsumption, basicHungerDamage, isHungry, isThirsty);
        this.plantConsumption = plantConsumption;
    }


    public void fulfillNeeds(List<Plant> plantList, Water waterSource){
        attemptEat(plantList);
        attemptDrink(waterSource);
    }

    private void attemptEat(List<Plant> plantList) {
        for (Plant plant : plantList) {
            if (plant.getAmount() > 0) {
                System.out.println(this.getName() + " ate " + plant.getType());
                plant.decreaseAmount(plantConsumption);
                eat(plantConsumption);
                return;
            }
        }
    }
    @Override
    public String toSaveFormat(){
        return "herbivore " + super.toSaveFormat() + " " + plantConsumption;
    }

}
