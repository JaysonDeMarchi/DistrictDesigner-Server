package algorithms;

import enums.Metric;
import enums.ShortName;
import regions.State;
import java.util.Map;
import managers.UpdateManager;

/**
 *
 * @author Jayson
 */
public abstract class Algorithm {

  State state;
  Map<Metric, Float> weights;
  UpdateManager updateManager;

  public Algorithm(ShortName shortName, Map<Metric, Float> weights) {
    this.state = new State(shortName);
    this.updateManager = new UpdateManager();
    this.weights = weights;
  }

  public abstract Boolean start();

  public abstract UpdateManager run();

  public UpdateManager getUpdateManager() {
    return this.updateManager;
  }

}
