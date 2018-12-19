package enums;

import algorithms.Algorithm;
import algorithms.RegionGrowing;
import algorithms.SimulatedAnnealing;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jayson
 */
public enum AlgorithmType {
  SIMULATED_ANNEALING {

    @Override
    public Algorithm createAlgorithm(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights, Integer numOfDistricts) {
      try {
        return new SimulatedAnnealing(shortName, selectionType, weights);
      } catch (Exception ex) {
        Logger.getLogger(AlgorithmType.class.getName()).log(Level.SEVERE, null, ex);
      }
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
