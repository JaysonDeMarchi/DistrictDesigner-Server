package algorithms;

import com.vividsolutions.jts.geom.Geometry;
import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import managers.UpdateManager;
import regions.District;
import regions.Precinct;

/**
 *
 * @author Jayson
 */
public class RegionGrowing extends Algorithm {

  List<Precinct> seeds;

  public RegionGrowing(ShortName shortName, SelectionType selectionType, Map<Metric, Float> weights, Integer numOfDistricts) throws Exception {
    super(shortName, selectionType, weights);
    this.state.setDistricts(new ArrayList<>());
    this.state.getPrecincts().forEach((precinct) -> precinct.setDistrictId(""));
  }

  @Override
  public Boolean start() {

    Precinct seed = (Precinct) ((List) this.state.getPrecincts()).get(100);
    System.out.println("seed is " + seed.getBoundary());
    try {
      District newDistrict = new District("ut-1", seed);
      newDistrict.setCandidatePrecincts(this.state.findAdjPrecincts(seed));
      double maxCompactness = 0.0;
      Precinct bestPrecinct = null;

      Iterator iterator = newDistrict.getCandidatePrecincts().iterator();
      while (iterator.hasNext()) {
        Precinct p = (Precinct) iterator.next();
        newDistrict.addPrecinct(p);
        Geometry districtShape = newDistrict.getGeometryShape();

        if (calCompactness(districtShape.getArea(), districtShape.getLength()) > maxCompactness) {
          maxCompactness = calCompactness(districtShape.getArea(), districtShape.getLength());
          bestPrecinct = p;
        }
        newDistrict.removePrecinct(p);
      }

      System.out.println("best precinct is " + bestPrecinct.getName());

    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

    return true;
  }

  @Override
  public UpdateManager run() {
    return this.getUpdateManager();
  }

  /**
   * This is used to get precincts randomly to start region growing according to
   * existing districts.
   *
   * @param districts
   */
  public void setSeedsRandomly(Collection<District> districts) {
    this.seeds = new ArrayList<>();
    for (District d : districts) {
      this.seeds.add(((List<Precinct>) (List) d.getPrecincts()).get(new Random().nextInt(d.getPrecincts().size())));
    }
  }

  /**
   * just for test
   */
  private double calCompactness(double area, double perimeter) {
    double r = Math.sqrt(area / Math.PI);
    double equalAreaPerimeter = 2 * Math.PI * r;
    double score = 1 / (perimeter / equalAreaPerimeter);
    return score;
  }

}
