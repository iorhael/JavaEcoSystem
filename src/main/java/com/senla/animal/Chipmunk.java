package com.senla.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.plant.BerryBush;
import com.senla.plant.Grass;
import com.senla.plant.Plant;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Chipmunk extends Herbivore {
    public Chipmunk(String name) {
        super(Chipmunk.class.getSimpleName() + name, 3);
    }

    @Override
    @JsonIgnore
    public List<Class<? extends Plant>> getPreferredPlants() {
        return Arrays.asList(BerryBush.class, Grass.class);
    }

}
