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

  private int id;
  private String name;
  private String shortName;
  private Collection<District> districts;
  private Collection<Precinct> precincts;

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
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
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

  @Transient
  public Collection<Precinct> getPrecincts() {
    return precincts;
  }

  public void setPrecincts(Collection<Precinct> precincts) {
    this.precincts = precincts;
  }

  public void initiatePrecinctsInDistrict() {
    for (Precinct p : this.getPrecincts()) {
      for (District d : this.getDistricts()) {
        if (p.getDistrictId().equals(d.getId())) {
          if (d.getPrecincts() == null) {
            d.setPrecincts((Set) new HashSet<>());
          } else {
            d.addPrecinct(p);
          }
        }
      }
    }
  }
  
  public Collection<Precinct> findAdjPrecincts(Precinct p){
    Set<Precinct> adjPrecincts = new HashSet<>();
    String[] adjPrecinctsId  = stringToList(p.getAdjPrecinctsList());
    for(String precinctId : adjPrecinctsId){
      adjPrecincts.add(findPrecinctById(precinctId,this.getPrecincts()));
    }
    return adjPrecincts;
  }

  private Precinct findPrecinctById(String id, Collection<Precinct> precincts){
    for(Precinct p: precincts){
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
