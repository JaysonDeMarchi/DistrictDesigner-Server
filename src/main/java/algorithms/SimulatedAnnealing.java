package algorithms;

import enums.Metric;
import enums.ShortName;
import java.util.EnumMap;
import java.util.Map;
import managers.UpdateManager;

/**
 *
 * @author Jayson
 */
public class SimulatedAnnealing extends Algorithm {

  public SimulatedAnnealing(ShortName shortName, EnumMap<Metric, Float> weights) throws Exception {
    super(shortName, weights);
  }

  @Override
  public Boolean start() {
    return true;
  }

  @Override
  public UpdateManager run() {
    return this.getUpdateManager();
  }
}
