package com.senla.animal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.senla.environment.Water;
import com.senla.plant.Plant;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Chipmunk.class, name = "chipmunk"),
        @JsonSubTypes.Type(value = Rabbit.class, name = "rabbit")
})
public abstract class Herbivore extends Animal {
    private int plantConsumption;

    public Herbivore(String name, int defense) {
        super(name, defense);
        this.plantConsumption = 1; // Each herbivore consumes one unit of plant
    }

    public abstract List<Class<? extends Plant>> getPreferredPlants();

    public void fulfillNeeds(List<Plant> plantList, Water waterSource){
        attemptEat(plantList);
        attemptDrink(waterSource);
    }

    private void attemptEat(List<Plant> plantList) {
        for (Plant plant : plantList) {
            if (getPreferredPlants().contains(plant.getClass()) && plant.getAmount() > 0) {
                System.out.println(this.getName() + " ate " + plant.getType());
                plant.decreaseAmount(plantConsumption);
                eat(plantConsumption);
                return;
            }
        }
    }

}
