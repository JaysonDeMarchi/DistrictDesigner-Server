package Algorithms;

import Enums.Metric;
import Enums.ShortName;
import java.util.HashMap;

/**
 *
 * @author Jayson
 */
public class RegionGrowing extends Algorithm {

  public RegionGrowing(ShortName shortName, HashMap<Metric, Float> weights) {
    super(shortName, weights);
  }

  @Override
  public Boolean start() {
    return true;
  }
}
