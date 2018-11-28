/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.HashMap;
import Enums.ShortName;
import Enums.AlgorithmType;
import Enums.Metric;

/**
 *
 * @author Jayson
 */
public class StartRequestParams {

  private AlgorithmType algoType;
  private ShortName shortName;
  private HashMap<Metric, Float> weights;

  public StartRequestParams(AlgorithmType a, ShortName s, HashMap<Metric, Float> w) {
    this.algoType = a;
    this.shortName = s;
    this.weights = w;
  }

  public AlgorithmType getAlgoType() {
    return this.algoType;
  }

  public ShortName getShortName() {
    return this.shortName;
  }

  public HashMap<Metric, Float> getWeights() {
    return this.weights;
  }

  public void setAlgoType(AlgorithmType a) {
    this.algoType = a;
  }

  public void setShortName(ShortName s) {
    this.shortName = s;
  }

  public void setWeights(HashMap<Metric, Float> w) {
    this.weights = w;
  }

  @Override
  public String toString() {
    return "Start Algorithm Request:"
            + "\n\tAlgorithm: " + this.getAlgoType()
            + "\n\tState: " + this.getShortName()
            + "\n\tWeights: " + this.getWeights();
  }
}
