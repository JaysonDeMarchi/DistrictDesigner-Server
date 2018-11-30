package algorithms;

import enums.Metric;
import enums.ShortName;
import regions.State;
import java.util.Map;

/**
 *
 * @author Jayson
 */
public abstract class Algorithm {

  State state;
  Map<Metric, Float> weights;

  public Algorithm(ShortName shortName, Map<Metric, Float> weights) {
    this.state = new State(shortName);
    this.weights = weights;
  }

  public abstract Boolean start();
}
