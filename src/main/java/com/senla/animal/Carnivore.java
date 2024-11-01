package com.senla.animal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.senla.AliveStatus;
import com.senla.environment.Water;
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
        @JsonSubTypes.Type(value = Weasel.class, name = "weasel"),
        @JsonSubTypes.Type(value = Wolf.class, name = "wolf")
})
public abstract class Carnivore extends Animal {

    public Carnivore(String name, int defense) {
        super(name, defense);
    }

    public abstract List<Class<? extends Animal>> getPreyTypes();

    public void fulfillNeeds(List<Herbivore> preyList, Water waterSource){
        attemptEat(preyList);
        attemptDrink(waterSource);
    }

    private void attemptEat(List<Herbivore> preyList) {
        for (Animal prey : preyList) {
            if (getPreyTypes().contains(prey.getClass()) && prey.isAlive()) {
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

}
