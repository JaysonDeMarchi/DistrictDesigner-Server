package enums;

import regions.District;
import regions.State;

/**
 *
 * @author Jayson
 */
public enum Metric {
  COMPACTNESS {
    @Override
    public Double getValue(District district, Double weight) {
      return 0.0;
    }
  },
  PARTISAN_GERRYMANDERING,
  POPULATION_EQUALITY,
  WASTED_VOTERS;

  public Double getValue(District district, Double weight) {
    return -1.0;
  }

  public Double getValue(State state, Double weight) {
    return -1.0;
  }
}
