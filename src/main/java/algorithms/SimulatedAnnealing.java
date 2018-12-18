package algorithms;

import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import java.util.EnumMap;
import java.util.Map;
import managers.UpdateManager;

/**
 *
 * @author Jayson
 */
public class SimulatedAnnealing extends Algorithm {


  public SimulatedAnnealing(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights) throws Exception {
    super(shortName, selectionType, weights);

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
