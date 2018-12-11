package algorithms;

import static enums.ComparisonType.*;
import enums.Metric;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import regions.State;
import java.util.Map;
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
  Collection<District> districts;
  Collection<Precinct> precincts;

  public Algorithm(ShortName shortName, Map<Metric, Float> weights) {

    this.weights = weights;
    this.precincts = new ArrayList<>();
    try {
      HibernateManager hb = new HibernateManager();
      QueryCondition queryCondition = new QueryCondition("shortName", shortName.toString(), EQUAL);
      this.setState(((List<State>) (List) hb.getObjectsByConditions(State.class, queryCondition)).get(0));
      queryCondition = new QueryCondition("state", shortName.toString(), EQUAL);
      this.setDistricts((Collection) hb.getObjectsByConditions(District.class, queryCondition));
      for (District d : this.districts) {
	queryCondition = new QueryCondition("districtId", d.getId(), EQUAL);
        //add adjprecinct here~
	Collection<Precinct> precinctsInDistrict = (Collection) hb.getObjectsByConditions(Precinct.class, queryCondition);
	d.setPrecincts((Collection) hb.getObjectsByConditions(Precinct.class, queryCondition));
	this.precincts.addAll(precinctsInDistrict);
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

  public Collection<Precinct> getPrecincts() {
    return this.precincts;
  }

  public void setPrecincts(Collection<Precinct> precincts) {
    this.precincts = precincts;
  }
  
  //move to State.class
  public Precinct findPrecinctById(String id, Collection<Precinct> Precincts) {
    for (Precinct p : Precincts) {
      if (p.getId().equals(id)) {
	return p;
      }
    }
    return null;
  }
  
  //remove "'" in database
  public String[] stringToList(String str){
    str = str.replaceAll("\\[|\\]|'", "");
    String[] resultList = str.split(",");
    return resultList;
  }

}
