package com.senla.animal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.senla.AliveStatus;
import com.senla.environment.Water;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Carnivore.class, name = "carnivore"),
        @JsonSubTypes.Type(value = Herbivore.class, name = "herbivore"),

        @JsonSubTypes.Type(value = Wolf.class, name = "wolf"),
        @JsonSubTypes.Type(value = Weasel.class, name = "weasel"),
        @JsonSubTypes.Type(value = Rabbit.class, name = "rabbit"),
        @JsonSubTypes.Type(value = Chipmunk.class, name = "chipmunk"),
})
public abstract class Animal {
    protected Double health;
    protected Integer defense;

    protected Integer daysWithoutWater = 0;
    protected Integer MAX_DAYS_WITHOUT_WATER = 4;

    protected String name;

    protected AliveStatus status = AliveStatus.ALIVE;

    protected Integer basicWaterConsumption = 5;
    protected Integer basicHungerDamage = 20;
    protected Boolean isHungry;
    protected Boolean isThirsty;
    protected static final Double MAX_HEALTH = 100.0;

    public Animal(String name, int defense) {
        this.health = MAX_HEALTH;
        this.defense = defense;
        this.isHungry = false;
        this.isThirsty = false;

        this.name = name;
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

//    public void setStatus(AliveStatus status){
//        this.status = status;
//    }
//    public AliveStatus getStatus(){
//        return status;
//    }

//    public double getHealth() {
//        return health;
//    }
//
    public boolean isAlive() {
        return status == AliveStatus.ALIVE;
    }
//
//    public int getDefense() {
//        return defense;
//    }
//
//    public void setHungry(boolean hungry) {
//        isHungry = hungry;
//    }

    public void regenerateHealth() {
        if (!isHungry && !isThirsty) {
            health += 50;
            if (health > MAX_HEALTH) {
                health = MAX_HEALTH;
            }
        }
    }

//    public String getName(){
//        return name;
//    }

    public void nextTurn(double hungerMod){
        if(isHungry){
            applyHungerDamage(hungerMod);
        }
        if(isThirsty){
            if(daysWithoutWater == MAX_DAYS_WITHOUT_WATER){
                System.out.println(name + " died from thirstiness");
                health = 0.0;
                status = AliveStatus.DEAD;
                return;
            }
            daysWithoutWater++;
        }
    }
}
