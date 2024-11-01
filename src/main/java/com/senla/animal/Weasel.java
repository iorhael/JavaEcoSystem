package com.senla.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Weasel extends Carnivore {
    public Weasel(String name) {
        super(Weasel.class.getSimpleName() + name, 5);
    }

    @Override
    @JsonIgnore
    public List<Class<? extends Animal>> getPreyTypes() {
        return Arrays.asList(Rabbit.class, Chipmunk.class);
    }

}
