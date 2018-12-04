package enums;

import java.time.Year;
import metrics.measures.Schwartzberg;
import metrics.results.DistrictMetricResult;
import regions.District;

/**
 *
 * @author Jayson
 */
public enum Metric {
  PARTISAN_GERRYMANDERING,
  POPULATION_EQUALITY,
  WASTED_VOTERS,
  POLSBY_POPPER_COMPACTNESS,
  SCHWARTZBERG_COMPACTNESS {
    @Override
    public DistrictMetricResult calculate(District district, Year year) {
      return Schwartzberg.calculate(district, year);
    }
  };

  public DistrictMetricResult calculate(District district, Year year) {
    return new DistrictMetricResult();
  }
}
