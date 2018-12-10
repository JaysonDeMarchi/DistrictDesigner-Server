package algorithms;

import enums.Metric;
import enums.ShortName;
import java.util.Map;

/**
 *
 * @author Jayson
 */
public class RegionGrowing extends Algorithm {

  public RegionGrowing(ShortName shortName, Map<Metric, Float> weights) {
    super(shortName, weights);
  }

  @Override
  public Boolean start() {
    return true;
  }
}
