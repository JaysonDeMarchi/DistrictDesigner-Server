/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import Enums.Metric;
import Enums.ShortName;
import java.util.HashMap;

/**
 *
 * @author Jayson
 */
public class SimulatedAnnealing extends Algorithm {

  public SimulatedAnnealing(ShortName shortName, HashMap<Metric, Float> weights) {
    super(shortName, weights);
  }

  @Override
  public Boolean start() {
    return true;
  }
}
