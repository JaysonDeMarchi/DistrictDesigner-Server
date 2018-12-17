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
  private Float compactness;
  private Float partisanGerrymandering;
  private Float populationEquality;


  public SaveWeightsParams() {
    super();
  }
 
  public SaveWeightsParams(String username, Float compactness, Float partisanGerrymandering, Float populationEquality) {
    this.compactness = compactness;
    this.partisanGerrymandering = partisanGerrymandering;
    this.populationEquality = populationEquality;
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

  public Float getPartisanGerrymandering() {
    return this.partisanGerrymandering;
  }

  public void setPartisanGerrymandering(Float partisanGerrymandering) {
    this.partisanGerrymandering = partisanGerrymandering;
  }

  public Float getPopulationEquality() {
    return this.populationEquality;
  }

  public void setPopulationEquality(Float populationEquality) {
    this.populationEquality = populationEquality;
  }
  
  







}
