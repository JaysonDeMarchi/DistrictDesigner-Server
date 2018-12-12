package algorithms;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import enums.Metric;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import regions.District;
import regions.Precinct;

/**
 *
 * @author Jayson
 */
public class RegionGrowing extends Algorithm {

  List<Precinct> seeds;

  public RegionGrowing(ShortName shortName, Map<Metric, Float> weights) {
    super(shortName, weights);
    this.start();
  }

  @Override
  public Boolean start() {

    Precinct seed = (Precinct) ((List) this.state.getPrecincts()).get(100);
    System.out.println("seed is " + seed.getBoundary());

    HashSet<Precinct> candidiatePrecicnts = new HashSet<>();
    candidiatePrecicnts.addAll(this.state.findAdjPrecincts(seed));

    try {
      District newDistrict = new District("ut-1", seed);
      double maxCompactness = 0.0;
      Precinct bestPrecinct = null;
      int i = 0;

      Iterator iterator = candidiatePrecicnts.iterator();
      while (iterator.hasNext()) {
        Precinct p = (Precinct) iterator.next();
        Geometry precinctBoundary = newDistrict.getReader().read(p.getBoundary());
        newDistrict.addGeoBoundary(precinctBoundary);
        GeometryCollection combinedDistrictPrecinct = (GeometryCollection) newDistrict.getGeometryFactory().buildGeometry(newDistrict.getGeoBoundary());
        Geometry tempDistrict = combinedDistrictPrecinct.union();

        if (calCompactness(tempDistrict.getArea(), tempDistrict.getLength()) > maxCompactness) {
          maxCompactness = calCompactness(tempDistrict.getArea(), tempDistrict.getLength());
          bestPrecinct = p;
        }
        newDistrict.removeGeoBoundary(precinctBoundary);

      }
      System.out.println("best precinct is " + bestPrecinct.getName());
    } catch (Exception ex) {
      System.out.println("here" + ex.getMessage());
    }

    return true;
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
