package com.senla.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.plant.Grass;
import com.senla.plant.Plant;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Rabbit extends Herbivore {
    public Rabbit(String name) {
        super(Rabbit.class.getSimpleName() + name, 5);
    }

    @Override
    @JsonIgnore
    public List<Class<? extends Plant>> getPreferredPlants() {
        return Arrays.asList(Grass.class);
    }

}
