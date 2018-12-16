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
      return new SimulatedAnnealing(shortName, weights);
    }
  },
  REGION_GROWING {
    @Override
    public Algorithm createAlgorithm(ShortName shortName, Map<Metric, Float> weights) {
      return new RegionGrowing(shortName, weights);
    }
  };

  public abstract Algorithm createAlgorithm(ShortName shortName, Map<Metric, Float> weights);
}
