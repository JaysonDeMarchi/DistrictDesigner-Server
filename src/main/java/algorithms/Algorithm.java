package algorithms;

import enums.Metric;
import enums.ShortName;
import java.util.Collection;
import java.util.HashMap;
import regions.State;
import java.util.Map;
import regions.District;
import regions.Precinct;
import utils.HibernateManager;

/**
 *
 * @author Jayson
 */
public abstract class Algorithm {

  State state;
  Map<Metric, Float> weights;
  Collection<District> districts;

  public Algorithm(ShortName shortName, Map<Metric, Float> weights) {
    this.state = new State(shortName);
    this.weights = weights;
    try {
      HibernateManager hb = new HibernateManager();
      Map<String, Object> criteria = new HashMap<>();
      criteria.put("state", this.getState().getShortName().toString());
      this.setDistricts((Collection<District>) (Collection) hb.getRecordsBasedOnCriteria(District.class, criteria));
      for (District d : this.districts) {
        criteria.put("districtId", d.getId());
        d.setPrecincts((Collection<Precinct>) (Collection) hb.getRecordsBasedOnCriteria(Precinct.class, criteria));
      }
    } catch (Throwable e) {
      System.out.println("Exception: " + e.getMessage());
    }
  }

  public abstract Boolean start();

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Map<Metric, Float> getWeights() {
    return weights;
  }

  public void setWeights(Map<Metric, Float> weights) {
    this.weights = weights;
  }

  public Collection<District> getDistricts() {
    return districts;
  }

  public void setDistricts(Collection<District> districts) {
    this.districts = districts;
  }

}
