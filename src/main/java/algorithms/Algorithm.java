package algorithms;

import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import java.util.Collection;
import java.util.EnumMap;
import regions.State;
import managers.UpdateManager;
import regions.District;
import utils.HibernateManager;

/**
 *
 * @author Jayson
 */
public abstract class Algorithm {

  private State originalState;
  private State state;
  private EnumMap<Metric, Float> weights;
  private UpdateManager updateManager;
  private Collection<District> districts;
  private SelectionType selectionType;

  public Algorithm(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights) throws Exception {
    this.weights = weights;
    this.updateManager = new UpdateManager();
    this.selectionType = selectionType;
    HibernateManager hb = HibernateManager.getInstance();
    this.originalState = hb.getStateByShortName(shortName);
    this.state = originalState.clone();
  }

  public abstract Boolean start();

  public abstract UpdateManager run();

  public abstract UpdateManager end();

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
