package algorithms;

import enums.Metric;
import enums.ShortName;
import java.util.Map;
import managers.UpdateManager;

/**
 *
 * @author Jayson
 */
public class SimulatedAnnealing extends Algorithm {

  public SimulatedAnnealing(ShortName shortName, Map<Metric, Float> weights) {
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
