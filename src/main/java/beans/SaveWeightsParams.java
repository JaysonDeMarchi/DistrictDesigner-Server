package beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hqzhu
 */
public class SaveWeightsParams {
  private String username;
  private String name;
  private Float compactness;
  private Float partisan_Gerrymandering;
  private Float population_Equality;

  public SaveWeightsParams() {
    super();
  }
 
  public SaveWeightsParams(String username, String name, Float compactness, Float partisan_Gerrymandering, Float population_Equality) {
    this.username = username;
    this.name = name;
    this.compactness = compactness;
    this.partisan_Gerrymandering = partisan_Gerrymandering;
    this.population_Equality = population_Equality;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Float getCompactness() {
    return this.compactness;
  }

  public void setCompactness(Float compactness) {
    this.compactness = compactness;
  }

  public Float getPartisan_Gerrymandering() {
    return this.partisan_Gerrymandering;
  }

  public void setPartisan_Gerrymandering(Float partisan_Gerrymandering) {
    this.partisan_Gerrymandering = partisan_Gerrymandering;
  }

  public Float getPopulation_Equality() {
    return this.population_Equality;
  }

  public void setPopulation_Equality(Float population_Equality) {
    this.population_Equality = population_Equality;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
}
