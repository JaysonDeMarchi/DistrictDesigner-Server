package algorithms;

import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import regions.State;
import java.util.Map;

/**
 *
 * @author Jayson
 */
public abstract class Algorithm {

  SelectionType selectionType;
  State state;
  Map<Metric, Float> weights;

  public Algorithm(SelectionType selectionType, ShortName shortName, Map<Metric, Float> weights) {
    this.selectionType = selectionType;
    this.state = new State(shortName);
    this.weights = weights;
  }

  public SelectionType getSelectionType() {
    return this.selectionType;
  }

  public State getState() {
    return this.state;
  }

  public abstract Boolean start();
}
