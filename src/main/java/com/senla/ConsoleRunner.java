package com.senla;

import com.senla.animal.Carnivore;
import com.senla.animal.Herbivore;
import com.senla.ecosystem.EcosystemController;
import com.senla.environment.WeatherStatus;
import com.senla.simulation.Simulation;
import com.senla.utils.AnimalBuilder;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleRunner {
    public static void main(String[] args) {
        System.out.println("Hello welcome to our simulation center");
        EcosystemController ecosystemController = basicConfig();
        try(Scanner scanner = new Scanner(System.in)){
            String input;
            boolean shouldExit = false;
            while(!shouldExit){
                input = scanner.nextLine();
                String[] inputTokens = input.split("\\s+");
                switch (inputTokens[0]){
                    case "print": {
                        try{
                            Long simulationId = Long.valueOf(inputTokens[1]);
                            ecosystemController.printSimulationState(simulationId);
                            break;
                        } catch (Exception e){
                            System.out.println("Exception reading all: " + e.getMessage());
                        }
                    }

                    case "addHerbivore": {
                        try{
                            Long simulationId = Long.valueOf(inputTokens[1]);
                            Herbivore h = AnimalBuilder.herbivoreBuilder(Arrays.copyOfRange(inputTokens, 1, inputTokens.length));
                            ecosystemController.addHerbivore(h, simulationId);
                            break;
                        } catch (Exception e){
                            System.out.println("Exception deleting all: " + e.getMessage());
                        }
                    }

                    case "addCarnivore": {
                        try{
                            Long simulationId = Long.valueOf(inputTokens[1]);
                            Carnivore c = AnimalBuilder.carnivoreBuilder(Arrays.copyOfRange(inputTokens, 1, inputTokens.length));
                            ecosystemController.addCarnivore(c, simulationId);
                            break;
                        } catch (Exception e){
                            System.out.println("Exception deleting all: " + e.getMessage());
                        }
                    }


                    case "delHerbivore": {
                        try{
                            Long simulationId = Long.valueOf(inputTokens[1]);
                            String name = inputTokens[2];
                            ecosystemController.delHerbivoreByName(name, simulationId);
                            System.out.println("Successfully deleted " + name);
                            break;
                        } catch (Exception e){
                            System.out.println("Exception deleting all: " + e.getMessage());
                        }
                    }
                    case "delCarnivore": {
                        try{
                            Long simulationId = Long.valueOf(inputTokens[1]);
                            String name = inputTokens[2];
                            ecosystemController.delCarnivoreByName(name, simulationId);
                            System.out.println("Successfully deleted " + name);
                            break;
                        } catch (Exception e){
                            System.out.println("Exception deleting all: " + e.getMessage());
                        }
                    }
//                    прерывание после 50 итераций
                    case "autoPlay": {
                        try{
                            Long simulationId = Long.valueOf(inputTokens[1]);
                            ecosystemController.automaticSimulationStart(simulationId);
                            break;
                        } catch (Exception e){
                            System.out.println("Exception while playing simulation all: " + e.getMessage());
                        }
                    }
                    case "oneTurn": {
                        try{
                            Long simulationId = Long.valueOf(inputTokens[1]);
                            ecosystemController.simulateOneTurn(simulationId);
                            break;
                        } catch (Exception e){
                            System.out.println("Exception simulating one turn all: " + e.getMessage());
                        }
                    }
                    case "nTurn": {
                        try{
                            Long simulationId = Long.valueOf(inputTokens[1]);
                            Integer n = Integer.parseInt(inputTokens[2]);
                            ecosystemController.simulateNTurn(simulationId, n);
                            break;
                        } catch (Exception e){
                            System.out.println("Exception simulating one turn all: " + e.getMessage());
                        }
                    }
                    case "count": {
                        try{
                            System.out.println("Number of simulations in memory: " + ecosystemController.countSimulations());
                            break;
                        } catch (Exception e){
                            System.out.println("Exception counting: " + e.getMessage());
                        }
                    }
                    case "exists": {
                        try{
                            Long simulationId = Long.valueOf(inputTokens[1]);
                            System.out.println(ecosystemController.exists(simulationId));
                            break;
                        } catch (Exception e){
                            System.out.println("Exception counting: " + e.getMessage());
                        }
                    }
                    case "loadAll": {
                        try{
                            ecosystemController.loadSimulationFromDb();
                            break;
                        } catch (Exception e){
                            System.out.println("Exception counting: " + e.getMessage());
                        }
                    }
                    case "persistAll": {
                        try{
                            ecosystemController.writeAllStatesToFile();
                            break;
                        } catch (Exception e){
                            System.out.println("Exception counting: " + e.getMessage());
                        }
                    }
                    case "exit": {
                        shouldExit = true;
                        break;
                    }
                    default:{
                        System.out.println("No such command");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("File not found - pls update file pos or smth else");
        }
    }
    private static EcosystemController basicConfig(){
        EcosystemController ecosystemController = new EcosystemController();
        Simulation sampleSimulation = new Simulation(0L, 2000, 2000, WeatherStatus.MODERATE);
        ecosystemController.addSimulation(sampleSimulation);
        return ecosystemController;
    }
}
