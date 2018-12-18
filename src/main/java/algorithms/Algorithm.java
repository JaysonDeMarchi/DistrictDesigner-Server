package algorithms;

import enums.Metric;
import enums.SelectionType;
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
  SelectionType selectionType;

  public Algorithm(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights) throws Exception {
    this.weights = weights;
    this.selectionType = selectionType;
    HibernateManager hb = HibernateManager.getInstance();
    this.state = hb.getStateByShortName(shortName);
    this.updateManager = new UpdateManager(this.state);
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

  public SelectionType getSelectionType() {
    return this.selectionType;
  }

  public void setSelectionType(SelectionType selectionType) {
    this.selectionType = selectionType;
  }

  public EnumMap<Metric, Float> getWeights() {
    return this.weights;
  }

  public void setWeights(EnumMap<Metric, Float> weights) {
    this.weights = weights;
  }

}
