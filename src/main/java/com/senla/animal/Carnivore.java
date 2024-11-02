package com.senla.animal;


import com.senla.AliveStatus;
import com.senla.environment.Water;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Carnivore extends Animal {

    public Carnivore(){}

    public Carnivore(String name, int defense) {
        super(name, defense);
    }

    public Carnivore(Double health, Integer defense, Integer daysWithoutWater, Integer MAX_DAYS_WITHOUT_WATER, String name, AliveStatus status, Integer basicWaterConsumption, Integer basicHungerDamage, Boolean isHungry, Boolean isThirsty){
        super(health, defense, daysWithoutWater, MAX_DAYS_WITHOUT_WATER, name, status, basicWaterConsumption, basicHungerDamage, isHungry, isThirsty);
    }

    public void fulfillNeeds(List<Herbivore> preyList, Water waterSource){
        attemptEat(preyList);
        attemptDrink(waterSource);
    }

    private void attemptEat(List<Herbivore> preyList) {
        for (Animal prey : preyList) {
            if (prey.isAlive()) {
                health -= prey.getDefense();
                if (health <= 0) {
                    health = 0.0;
                    this.setStatus(AliveStatus.DEAD);
                    System.out.println("Predator: " + this.getName() + " died trying to eat prey: " + prey.getName());
                }
                if(this.status == AliveStatus.ALIVE){
                    prey.setStatus(AliveStatus.DEAD);
                    System.out.println("Prey: " + prey.getName() + " is eaten by: " + this.getName() + " - removing prey from ecosystem");
                    prey.health = 0.0;
                    isHungry = false;
                    preyList.remove(prey);
                }
                return;
            }
        }
        isHungry = true;
    }
    @Override
    public String toSaveFormat(){
        return "carnivore " + super.toSaveFormat();
    }

}
