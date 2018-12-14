package regions;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * @author Hengqi Zhu
 */
@Entity
@Table(name = "PRECINCT")
public class Precinct implements Serializable {

  private String id;
  private String name;
  private String boundary;
  private String stateName;
  private String districtId;
  private String adjPrecinctsList;


  public Precinct() {
  }

  public Precinct(String precinctId, String stateName, String boundary, String adjPrecincts) throws Exception {
    this.id = precinctId;
    this.boundary = boundary;
    this.stateName = stateName;
    this.adjPrecinctsList = adjPrecincts;
  }

  @Id
  @GeneratedValue
  @Column(name = "ID")
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "NAME")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "BOUNDARY")
  public String getBoundary() {
    return this.boundary;
  }

  public void setBoundary(String boundary) {
    this.boundary = boundary;
  }

  @Column(name = "DISTRICT")
  public String getDistrictId() {
    return this.districtId;
  }

  public void setDistrictId(String districtId) {
    this.districtId = districtId;
  }

  @Column(name = "ADJPRECINCTS")
  public String getAdjPrecinctsList() {
    return this.adjPrecinctsList;
  }

  public void setAdjPrecinctsList(String adjPrecinctsList) {
    this.adjPrecinctsList = adjPrecinctsList;
  }

  @Column(name = "STATE")
  public String getStateName() {
    return this.stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  } 
  
}
