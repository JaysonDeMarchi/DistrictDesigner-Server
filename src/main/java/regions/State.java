package regions;

import electionResults.HouseResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
public class State implements Serializable {

  private Integer id;
  private String name;
  private String shortName;
  private Integer population;
  private Collection<District> districts;
  private Collection<Precinct> precincts;
  private ConstitutionRequirements constitutionRequirements;
  private Collection<ConstitutionText> constitutionTexts;
  private Collection<HouseResult> houseResult;


  public State() {
    this.constitutionRequirements = new ConstitutionRequirements();
    this.constitutionTexts = new ArrayList<>();
    this.houseResult = new ArrayList<>();
  }

  public State(int id, String name) {
    this.constitutionRequirements = new ConstitutionRequirements();
    this.constitutionTexts = new ArrayList<>();
    this.houseResult = new ArrayList<>();
    this.id = id;
    this.name = name;
  }

  public State(String shortName) {
    this.constitutionRequirements = new ConstitutionRequirements();
    this.constitutionTexts = new ArrayList<>();
    this.houseResult = new ArrayList<>();
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

  @Column(name="POPULATION")
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

  @Transient
  public Collection<HouseResult> getHouseResult() {
    return houseResult;
  }

  public void setHouseResult(Collection<HouseResult> houseResult) {
    this.houseResult = houseResult;
  }
  
  public void initiatePrecinctsInDistrict() {
    for (Precinct p : this.getPrecincts()) {
      this.getDistrictById(p.getDistrictId()).addPrecinct(p);
    }
  }

  public Collection<Precinct> findAdjPrecincts(Precinct p) {
    Set<Precinct> adjPrecincts = new HashSet<>();
    String[] adjPrecinctsId = stringToList(p.getAdjPrecinctsList());
    for (String precinctId : adjPrecinctsId) {
      adjPrecincts.add(getPrecinctById(precinctId));
    }
    return adjPrecincts;
  }

  private District getDistrictById(String id) {
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
}
