package algorithms;

import enums.Metric;
import enums.ShortName;
import java.util.Collection;
import java.util.EnumMap;
import regions.State;
import java.util.Map;
import managers.UpdateManager;
import regions.District;
import utils.HibernateManager;

/**
 *
 * @author Jayson
 */
public abstract class Algorithm {

  State state;
  EnumMap<Metric, Float> weights;
  UpdateManager updateManager;
  Collection<District> districts;

  public Algorithm(ShortName shortName, EnumMap<Metric, Float> weights) throws Exception {
    this.weights = weights;
    this.updateManager = new UpdateManager();
    HibernateManager hb = HibernateManager.getInstance();
    this.state = hb.getStateByShortName(shortName);
  }

  public abstract Boolean start();

  public abstract UpdateManager run();

  public UpdateManager getUpdateManager() {
    return this.updateManager;
  }

  public State getState() {
    return this.state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Map<Metric, Float> getWeights() {
    return this.weights;
  }

  public void setWeights(EnumMap<Metric, Float> weights) {
    this.weights = weights;
  }

}
