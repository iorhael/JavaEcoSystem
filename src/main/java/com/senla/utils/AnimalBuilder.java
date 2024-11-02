package com.senla.utils;

import com.senla.AliveStatus;
import com.senla.animal.Animal;
import com.senla.animal.Carnivore;
import com.senla.animal.Herbivore;

public class AnimalBuilder {
    public static <T extends Animal> void animalSuperTypeBuilder(T animal, String[] tokenArray){
        animal.setName(tokenArray[1]);
        animal.setHealth(Double.valueOf(tokenArray[2]));
        animal.setDefense(Integer.valueOf(tokenArray[3]));
        animal.setDaysWithoutWater(Integer.valueOf(tokenArray[4]));
        animal.setMaxDaysWithoutWater(Integer.valueOf(tokenArray[5]));
        animal.setStatus(AliveStatus.valueOf(tokenArray[6]));
        animal.setBasicWaterConsumption(Integer.valueOf(tokenArray[7]));
        animal.setBasicHungerDamage(Integer.valueOf(tokenArray[8]));
        animal.setIsHungry(Boolean.valueOf(tokenArray[9]));
        animal.setIsThirsty(Boolean.valueOf(tokenArray[10]));
    }

    public static Herbivore herbivoreBuilder(String[] tokenArray){
        Herbivore h = new Herbivore();
        animalSuperTypeBuilder(h, tokenArray);
        h.setPlantConsumption(Integer.valueOf(tokenArray[11]));
        return h;
    }
    public static Carnivore carnivoreBuilder(String[] tokenArray){
        Carnivore c = new Carnivore();
        animalSuperTypeBuilder(c, tokenArray);
        return c;
    }
}
