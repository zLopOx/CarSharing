package fr.zankia.carsharing.process;

import fr.zankia.carsharing.Controller;
import fr.zankia.carsharing.model.CityState;
import fr.zankia.carsharing.model.ICityState;
import fr.zankia.carsharing.model.IPassenger;
import fr.zankia.carsharing.model.IVehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Deterministic algorithm. This algorithm explores all the solution and give the most efficient.
 * It could take a lot of time if there are a lot of model to process.
 * @see <a href="https://en.wikipedia.org/wiki/Deterministic_algorithm">Deterministic algorithm</a>
 * @since 0.1
 */
public class Deterministic implements Algorithm {
    private static String name = "Deterministic";
    /**
     * The logger of this class
     */
    private static final Logger log = Logger.getLogger(Controller.class.getName());


    @Override
    public String toString() {
        return name + " algorithm";
    }


    @Override
    public CityState step() {
        //TODO
        return null;
    }


    @Override
    public void solve() {
        ICityState state = Controller.getInstance().getCityState();
        List<IVehicle> vehicles = state.getVehicles();
        List<IPassenger> passengers = state.getWaypoints();
        List<IVehicle> bestSolution = null;
        double minimumCost = Double.MAX_VALUE;
        for (int j = 0; j < factorial(vehicles.size()); ++j) {
        	for (int g = 0; g < factorial(passengers.size()); g++) {
        		List<IVehicle> currentSolution = createCurrentSolution(vehicles, passengers);
                double totalCost = 0;
                for (IVehicle vehicle : currentSolution) {
                    totalCost += vehicle.getCost();
                }
                if (minimumCost > totalCost) {

                    minimumCost = totalCost;
                    bestSolution = currentSolution;
                }
                clearVehicles(vehicles);
                
                //Permute randomly passengers
                Collections.shuffle(passengers);
        	}
        	
            //Permute randomly vehicles
            Collections.shuffle(vehicles);
            
            //swap(vehicles, j, j+1);
        }
            
        state.setVehicles(bestSolution);
    }
  
    //Create current solution to test 
    private List<IVehicle> createCurrentSolution(List<IVehicle> vehicles, List<IPassenger> passengers){
    	int currentVehicle = 0;
        List<IVehicle> currentSolution = vehicles;
        for (int i = 0; i < passengers.size(); ++i) {
            try {
                currentSolution.get(currentVehicle).addRoute(passengers.get(i));
            } catch (IllegalStateException e) {
                ++currentVehicle;
                --i;
            }
        }
        return currentSolution;
    }
    
    //Calculate number of permuteation of a List
    private double factorial(int n) {
    	double fact = 1;
    	for (int i=1; i<=n;i++) {
    		fact *= i;
    	}
    	return fact;
    }
    
    //clear route of all vehicles
    private void clearVehicles(List<IVehicle> vehicles) {
    	for (IVehicle vehicle : vehicles) {
    		vehicle.clear();
    	}
    }
    
    /*private void swap(List<IVehicle> vehicles, int i, int j) {
    	if (j == vehicles.size()) {
        	j = 0;
    	}
    	IVehicle v = vehicles.get(i);
    	vehicles.set(i, vehicles.get(j));
    	vehicles.set(j, v);
	}*/
}
