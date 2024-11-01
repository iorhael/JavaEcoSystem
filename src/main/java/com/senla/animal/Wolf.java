package com.senla.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Wolf extends Carnivore {
    public Wolf(String name) {
        super(Wolf.class.getSimpleName() + name, 15);
    }

    @Override
    @JsonIgnore
    public List<Class<? extends Animal>> getPreyTypes() {
        return Arrays.asList(Rabbit.class, Chipmunk.class);
    }

}
