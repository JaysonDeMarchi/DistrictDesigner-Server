package algorithms;

import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import java.util.Map;

/**
 *
 * @author Jayson
 */
public class SimulatedAnnealing extends Algorithm {

  public SimulatedAnnealing(SelectionType selectionType, ShortName shortName, Map<Metric, Float> weights) {
    super(selectionType, shortName, weights);
  }

  @Override
  public Boolean start() {
    return true;
  }
}
