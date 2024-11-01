package com.senla.plant;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Tree extends Plant {
    public Tree(int amount) {
        super(amount, "Tree");
    }
}
