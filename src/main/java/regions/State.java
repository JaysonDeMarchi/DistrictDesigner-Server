package regions;

import enums.Metric;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import politics.ConstitutionRequirements;
import politics.ConstitutionText;

/**
 * @author Hengqi Zhu
 */
@Entity
@Table(name = "STATE")
public class State implements Serializable, Cloneable {

  private Integer id;
  private String name;
  private String shortName;
  private Integer population;
  private Collection<District> districts;
  private Collection<Precinct> precincts;
  private ConstitutionRequirements constitutionRequirements;
  private Collection<ConstitutionText> constitutionTexts;
  private Double objectiveFunction;

  public State() {
    this.constitutionRequirements = new ConstitutionRequirements();
    this.constitutionTexts = new ArrayList<>();
    this.districts = new ArrayList<>();
  }

  public State(int id, String name) {
    this.constitutionRequirements = new ConstitutionRequirements();
    this.constitutionTexts = new ArrayList<>();
    this.id = id;
    this.name = name;
  }

  public State(String shortName) {
    this.constitutionRequirements = new ConstitutionRequirements();
    this.constitutionTexts = new ArrayList<>();
    this.shortName = shortName;
  }

  @Id
  @GeneratedValue
  @Column(name = "ID")
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "NAME")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "SHORTNAME")
  public String getShortName() {
    return this.shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @Transient
  public Collection<District> getDistricts() {
    return this.districts;
  }

  public void setDistricts(Collection<District> districts) {
    this.districts = districts;
  }

  @Column(name = "POPULATION")
  public Integer getPopulation() {
    return population;
  }

  public void setPopulation(Integer population) {
    this.population = population;
  }

  @Transient
  public ConstitutionRequirements getConstitutionRequirements() {
    return this.constitutionRequirements;
  }

  public void setConstitutionRequirements(ConstitutionRequirements requirements) {
    this.constitutionRequirements = requirements;
  }

  @Transient
  public Collection<Precinct> getPrecincts() {
    return precincts;
  }

  public void setPrecincts(Collection<Precinct> precincts) {
    this.precincts = precincts;
  }

  public void initiatePrecinctsInDistrict() {
    for (Precinct p : this.getPrecincts()) {
      this.getDistrictById(p.getDistrictId()).addPrecinct(p);
      this.findAdjPrecincts(p);
    }
  }

  public Collection<Precinct> findAdjPrecincts(Precinct p) {
    Set<Precinct> adjPrecincts = new HashSet<>();
    String[] adjPrecinctsId = stringToList(p.getAdjPrecinctsList());
    for (String precinctId : adjPrecinctsId) {
      adjPrecincts.add(getPrecinctById(precinctId));
      p.getAdjPrecincts().add(getPrecinctById(precinctId));
    }
    return adjPrecincts;
  }

  public District getDistrictById(String id) {
    for (District d : this.getDistricts()) {
      if (d.getId().equals(id)) {
        return d;
      }
    }
    return null;
  }

  private Precinct getPrecinctById(String id) {
    for (Precinct p : this.getPrecincts()) {
      if (p.getId().equals(id)) {
        return p;
      }
    }
    return null;
  }

  private String[] stringToList(String str) {
    str = str.replaceAll("\\[|\\]|'", "");
    String[] resultList = str.split(",");
    return resultList;
  }

  @Transient
  public Collection<ConstitutionText> getConstitutionTexts() {
    return this.constitutionTexts;
  }

  public void setConstitutionTexts(Collection<ConstitutionText> texts) {
    this.constitutionTexts = texts;
  }

  @Transient
  public Double getObjectiveFunction() {
    return this.objectiveFunction;
  }

  public void setObjectiveFunction(Double objectiveFunction) {
    this.objectiveFunction = objectiveFunction;
  }

  public Double calculateObjectiveFunction(EnumMap<Metric, Float> weights) {
    Double objectiveFunc = 0.0;
    Integer validMetrics = 0;
    for (Metric metric : Metric.values()) {
      Double result = metric.getValue(this, weights.get(metric));
      if (result >= 0.0) {
        objectiveFunc += metric.getValue(this, weights.get(metric));
        validMetrics++;
      }
    }
    return objectiveFunc / validMetrics;
  }

  @Override
  public State clone() throws CloneNotSupportedException {
    State newState = new State();
    newState.setConstitutionRequirements(this.getConstitutionRequirements());
    newState.setConstitutionTexts(this.getConstitutionTexts());
    newState.setId(this.getId());
    newState.setName(this.getName());
    newState.setObjectiveFunction(this.getObjectiveFunction());
    newState.setPopulation(this.getPopulation());
    newState.setPrecincts(this.getPrecincts());
    newState.setShortName(this.getShortName());
    return newState;
  }
}
