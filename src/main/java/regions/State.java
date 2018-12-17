package regions;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Hengqi Zhu
 */
@Entity
@Table(name = "STATE")
public class State implements Serializable {


  private Integer id;
  private String name;
  private String shortName;
  private Collection<District> districts;
  private Collection<Precinct> precincts;
  private Integer population;


  public State() {
  }

  public State(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public State(String shortName) {
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

  @Column(name = "POPULATION")
  public Integer getPopulation() {
    return this.population;
  }

  public void setPopulation(Integer population) {
    this.population = population;
  }
  
  @Column(name="SHORTNAME")
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
    }
  }
  
  public Collection<Precinct> findAdjPrecincts(Precinct p){
    Set<Precinct> adjPrecincts = new HashSet<>();
    String[] adjPrecinctsId  = stringToList(p.getAdjPrecinctsList());
    for(String precinctId : adjPrecinctsId){
      adjPrecincts.add(getPrecinctById(precinctId));
    }
    return adjPrecincts;
  }
  
  
  private District getDistrictById(String id){
    for(District d : this.getDistricts()){
      if(d.getId().equals(id))
        return d;
    }
    return null;
  }

  private Precinct getPrecinctById(String id){
    for(Precinct p: this.getPrecincts()){
      if(p.getId().equals(id))
        return p;
    }
    return null;
  }
  
  private String[] stringToList(String str){
    str = str.replaceAll("\\[|\\]|'", "");
    String[] resultList = str.split(",");
    return resultList;
  }
  
}
