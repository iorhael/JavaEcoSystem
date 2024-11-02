package com.senla.ecosystem;


import com.senla.animal.Carnivore;
import com.senla.animal.Herbivore;
import com.senla.simulation.Simulation;
import com.senla.utils.SimulationBuilder;
import lombok.*;

import java.io.*;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EcosystemController {

//    private static Long nameGen = 1L;
    private List<Simulation> simulations = new ArrayList<>();

    public EcosystemController(Simulation sampleSimulation) {
        this.simulations.add(sampleSimulation);
    }

    public void addSimulation(Simulation simulation){
        simulations.add(simulation);
    }

    public void delSimulation(Long simulationId){
        Simulation sim = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst()
                .orElseThrow(()->new RuntimeException("Simulation Not found"));
        simulations.remove(sim);
    }

    public void printSimulationState(Long simulationId){
        Simulation sim = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst()
                .orElseThrow(()->new RuntimeException("Simulation Not found"));
        sim.printEcosystemStatus();
    }

    public void addHerbivore(Herbivore h, Long simulationId){
        Simulation sim = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst()
                .orElseThrow(()->new RuntimeException("Simulation Not found"));
        sim.getHerbivores().add(h);
    }

    public void addCarnivore(Carnivore c, Long simulationId){
        Simulation sim = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst()
                .orElseThrow(()->new RuntimeException("Simulation Not found"));
        sim.getCarnivores().add(c);
    }

    public void delCarnivoreByName(String name, Long simulationId){
        Simulation sim = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst()
                .orElseThrow(()->new RuntimeException("Simulation Not found"));
        List<Carnivore> carnivores = sim.getCarnivores();
        Carnivore carnivoreToDelete = carnivores.stream().filter(c -> c.getName().equals(name)).findFirst()
                .orElseThrow(()->new RuntimeException("Carnivore Not Found"));
        carnivores.remove(carnivoreToDelete);
    }

    public void delHerbivoreByName(String name, Long simulationId){
        Simulation sim = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst()
                .orElseThrow(()->new RuntimeException("Simulation Not found"));
        List<Herbivore> herbivores = sim.getHerbivores();
        Herbivore herbivoreToDelete = herbivores.stream().filter(c -> c.getName().equals(name)).findFirst()
                .orElseThrow(()->new RuntimeException("Carnivore Not Found"));
        herbivores.remove(herbivoreToDelete);
    }


    public void automaticSimulationStart(Long simulationId) throws InterruptedException {
        Simulation simulation = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst()
                .orElseThrow(()->new RuntimeException("No simulation found"));
        for(int i = 0; i < 50; i++){
            if(simulation.getHerbivores().isEmpty() && simulation.getCarnivores().isEmpty()) {
                System.out.println("Simulation ended due to All extinction");
                return;
            }
            Thread.sleep(50);
            simulation.simulateTurn();
        }
        System.out.println("Automatic simulation paused for number of iterations depleted");
    }

    public void simulateOneTurn(Long simulationId) {
        Simulation simulation = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst()
                .orElseThrow(()->new RuntimeException("No simulation found"));
        simulation.simulateTurn();
    }

    public void simulateNTurn(Long simulationId, Integer N) throws InterruptedException {
        Simulation simulation = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst()
                .orElseThrow(()->new RuntimeException("No simulation found"));

        for(int i = 0; i < N; i++){
            if(simulation.getHerbivores().isEmpty() && simulation.getCarnivores().isEmpty()) {
                System.out.println("Simulation ended due to All extinction");
                return;
            }
            Thread.sleep(100);
            simulation.simulateTurn();
        }
    }

    public Integer countSimulations(){
        return simulations.size();
    }

    public Boolean exists(Long simulationId){
        Optional<Simulation> simulation = simulations.stream().filter(s->s.getId().equals(simulationId)).findFirst();
        return simulation.isPresent();
    }

    public void loadSimulationFromDb() {
        try(Scanner scanner = new Scanner(new File("db.txt"))){
            SimulationBuilder sb = new SimulationBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.readSimulations(line);
            }
            this.simulations = sb.getBuiltSimulations();
            int a = 5;
        } catch (Exception e) {
            System.out.println("File not found - pls update file pos or smth else");
        }
    }

    public void writeAllStatesToFile(){
        try(FileWriter fw = new FileWriter("db.txt")){
            for(Simulation s: simulations){
                fw.append("---\n");
                fw.append(s.simulationToState());
            }
            fw.append("---");
        } catch (IOException e) {
            System.out.println("File not found - pls update file pos or smth else");
        }
    }

}
