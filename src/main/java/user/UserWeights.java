package user;

import enums.Metric;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author hqzhu
 */
@Entity
@Table(name = "USER_WEIGHTS")
public class UserWeights implements Serializable {

  private String id;
  private String username;
  private String name;
  private Float compactness;
  private Float populationEquality;
  private Float partisanGerrymandering;
  
  public UserWeights(){}

  public UserWeights(String username, String name, Float compactness, Float populationEquality, Float partisanGerrymandering) {
    this.username = username;
    this.name = name;
    this.compactness = compactness;
    this.populationEquality = populationEquality;
    this.partisanGerrymandering = partisanGerrymandering;
  }
  
  @Id
  @GeneratedValue
  @Column(name = "ROWID")
  public String getId() { return this.id; }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "USERNAME")
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Column(name = "NAME")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "compactness")
  public Float getCompactness() {
    return this.compactness;
  }

  public void setCompactness(Float compactness) {
    this.compactness = compactness;
  }

  @Column(name = "population_Equality")
  public Float getPopulationEquality() {
    return this.populationEquality;
  }

  public void setPopulationEquality(Float populationEquality) {
    this.populationEquality = populationEquality;
  }

  @Column(name = "PARTISAN_FAIRNESS")
  public Float getPartisanGerrymandering() {
    return this.partisanGerrymandering;
  }

  public void setPartisanGerrymandering(Float partisanGerrymandering) {
    this.partisanGerrymandering = partisanGerrymandering;
  }
  
  @Transient
  public HashMap<Metric,Float> getUserWeightsList(){
    HashMap<Metric,Float> userWeights = new HashMap<>();
    userWeights.put(Metric.compactness,this.compactness);
    userWeights.put(Metric.population_Equality, this.populationEquality);
    userWeights.put(Metric.partisan_Gerrymandering, this.partisanGerrymandering);
    return userWeights;
  }
  
   public void setUserWeights(HashMap<Metric,Float> userWeightsList) {
     this.compactness = userWeightsList.get(Metric.compactness);
     this.populationEquality = userWeightsList.get(Metric.population_Equality);
     this.partisanGerrymandering = userWeightsList.get(Metric.partisan_Gerrymandering);
   }
}
