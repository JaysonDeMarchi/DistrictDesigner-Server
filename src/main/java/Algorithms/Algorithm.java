package Algorithms;

import Enums.Metric;
import Enums.ShortName;
import Regions.State;
import java.util.HashMap;

/**
 *
 * @author Jayson
 */
public abstract class Algorithm {

  State state;
  HashMap<Metric, Float> weights;

  public Algorithm(ShortName shortName, HashMap<Metric, Float> weights) {
    this.state = new State(shortName);
    this.weights = weights;
  }

  public abstract Boolean start();
}
