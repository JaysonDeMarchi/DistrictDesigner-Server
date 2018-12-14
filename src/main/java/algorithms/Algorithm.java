package algorithms;

import enums.ComparisonType;
import enums.Metric;
import enums.QueryField;
import enums.ShortName;
import java.util.Collection;
import java.util.List;
import regions.State;
import java.util.Map;
import managers.UpdateManager;
import regions.District;
import regions.Precinct;
import utils.HibernateManager;
import utils.QueryCondition;

/**
 *
 * @author Jayson
 */
public abstract class Algorithm {

  State state;
  Map<Metric, Float> weights;
  UpdateManager updateManager;
  Collection< District> districts;

  public Algorithm(ShortName shortName, Map<Metric, Float> weights) {
    this.weights = weights;
    try {
      HibernateManager hb = new HibernateManager();

      QueryCondition queryCondition = new QueryCondition(QueryField.shortName, shortName.toString(), ComparisonType.EQUAL);
      this.setState((State) ((List) hb.getObjectsByConditions(State.class, queryCondition)).get(0));
      queryCondition = new QueryCondition(QueryField.stateName, shortName.toString(), ComparisonType.EQUAL);
      this.state.setDistricts((Collection) hb.getObjectsByConditions(District.class, queryCondition));
      queryCondition = new QueryCondition(QueryField.stateName, shortName.toString(), ComparisonType.EQUAL);
      this.state.setPrecincts((Collection) hb.getObjectsByConditions(Precinct.class, queryCondition));
      this.state.initiatePrecinctsInDistrict();
    } catch (Throwable e) {
      System.out.println(e.getMessage());
    }
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

  public void setWeights(Map<Metric, Float> weights) {
    this.weights = weights;
  }

}
