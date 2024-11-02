package com.senla.animal;

import com.senla.AliveStatus;
import com.senla.environment.Water;
import com.senla.settings.GlobalSettings;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
public abstract class Animal {
    protected Double health;
    protected Integer defense;
    protected Integer daysWithoutWater = 0;
    protected Integer maxDaysWithoutWater = 4;
    protected String name;
    protected AliveStatus status = AliveStatus.ALIVE;
    protected Integer basicWaterConsumption = 5;
    protected Integer basicHungerDamage = 20;
    protected Boolean isHungry;
    protected Boolean isThirsty;

//    protected static final Double MAX_HEALTH = 100.0;

    public Animal(){}

    public Animal(String name, int defense) {
        this.health = GlobalSettings.MAX_HEALTH;
        this.defense = defense;
        this.isHungry = false;
        this.isThirsty = false;

        this.name = name;
    }

    public Animal(Double health, Integer defense, Integer daysWithoutWater, Integer MAX_DAYS_WITHOUT_WATER, String name, AliveStatus status, Integer basicWaterConsumption, Integer basicHungerDamage, Boolean isHungry, Boolean isThirsty) {
        this.health = health;
        this.defense = defense;
        this.daysWithoutWater = daysWithoutWater;
        this.maxDaysWithoutWater = MAX_DAYS_WITHOUT_WATER;
        this.name = name;
        this.status = status;
        this.basicWaterConsumption = basicWaterConsumption;
        this.basicHungerDamage = basicHungerDamage;
        this.isHungry = isHungry;
        this.isThirsty = isThirsty;
    }

    protected void applyHungerDamage(double hungerMod) {
        if (isHungry) {
            health -= hungerMod * basicHungerDamage;
            if (health <= 0) {
                health = 0.0; // Health cannot be negative
                System.out.println("Death due to starvation: " + name);
                status = AliveStatus.DEAD;
            }
        }
    }

    protected void attemptDrink(Water waterSource){
        if(waterSource.getAmount() < basicWaterConsumption){
            waterSource.setAmount(0);
            System.out.println("No water sources left");
            isThirsty = true;
            daysWithoutWater++;
        } else{
            daysWithoutWater = 0;
            waterSource.setAmount(waterSource.getAmount() - basicWaterConsumption);
            isThirsty = false;
        }
    }


    public void eat(int foodAmount) {
        if (foodAmount > 0) {
            isHungry = false;
        }
    }

    public boolean isAlive() {
        return status == AliveStatus.ALIVE;
    }


    public void regenerateHealth() {
        if (!isHungry && !isThirsty) {
            health += 50;
            if (health > GlobalSettings.MAX_HEALTH) {
                health = GlobalSettings.MAX_HEALTH;
            }
        }
    }

    public void nextTurn(double hungerMod){
        if(isHungry){
            applyHungerDamage(hungerMod);
        }
        if(isThirsty){
            if(Objects.equals(daysWithoutWater, maxDaysWithoutWater)){
                System.out.println(name + " died from thirstiness");
                health = 0.0;
                status = AliveStatus.DEAD;
                return;
            }
            daysWithoutWater++;
        }
    }


    public String toSaveFormat() {
        return  " " + name +
                " " + health +
                " " + defense +
                " " + daysWithoutWater +
                " " + maxDaysWithoutWater +
                " " + status +
                " " + basicWaterConsumption +
                " " + basicHungerDamage +
                " " + isHungry +
                " " + isThirsty;
    }
}
