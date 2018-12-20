package enums;

import algorithms.Algorithm;
import algorithms.RegionGrowing;
import algorithms.SimulatedAnnealing;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jayson
 */
public enum AlgorithmType {
  SIMULATED_ANNEALING {

    public Algorithm createAlgorithm(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights, Integer numOfDistricts) {
      try {
        System.out.println("Type Simulated Annealing");
        return new SimulatedAnnealing(shortName, selectionType, weights);
      } catch (Exception ex) {
        Logger.getLogger(AlgorithmType.class.getName()).log(Level.SEVERE, null, ex);
      }
      System.out.println("Cannot create algorithm");
      return null;
    }
  },
  REGION_GROWING {
    @Override
    public Algorithm createAlgorithm(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights, Integer numOfDistricts) {
      try {
        System.out.println("Type Region Growing");
        return new RegionGrowing(shortName, selectionType, weights, numOfDistricts);
      } catch (Exception ex) {
        Logger.getLogger(AlgorithmType.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
    }
  };

  public abstract Algorithm createAlgorithm(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights, Integer numOfDistricts);
}
