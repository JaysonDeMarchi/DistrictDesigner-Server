package metrics.measures;

import java.time.Year;
import metrics.results.DistrictMetricResult;
import regions.District;

/**
 *
 * @author Jayson
 */
public abstract class Compactness extends Measure {

  protected DistrictMetricResult calculate(District district, Year year) {
    double districtArea = district.getArea();
    double districtPerimeter = district.getPerimeter();
    double score = calculate(districtArea, districtPerimeter);
    DistrictMetricResult result = new DistrictMetricResult(district);
    result.setScore(score);
    return result;
  }

  public abstract Double calculate(double area, double perimeter);
}
