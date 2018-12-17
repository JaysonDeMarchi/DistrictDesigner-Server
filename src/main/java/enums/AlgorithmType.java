package enums;

import algorithms.Algorithm;
import algorithms.RegionGrowing;
import algorithms.SimulatedAnnealing;
import java.util.Map;

/**
 *
 * @author Jayson
 */
public enum AlgorithmType {
  SIMULATED_ANNEALING {
    @Override
    public Algorithm createAlgorithm(ShortName shortName, Map<Metric, Float> weights) {
      try {
        return new SimulatedAnnealing(shortName, weights);
      } catch (Exception ex) {
        Logger.getLogger(AlgorithmType.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
    }
  },
  REGION_GROWING {
    @Override
    public Algorithm createAlgorithm(ShortName shortName, Map<Metric, Float> weights) {
      try {
        return new RegionGrowing(shortName, weights);
      } catch (Exception ex) {
        Logger.getLogger(AlgorithmType.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
    }
  };

  public abstract Algorithm createAlgorithm(ShortName shortName, Map<Metric, Float> weights);
}
