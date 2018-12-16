package algorithms;

import enums.Metric;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONWriter;
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
    int counter=0;
    District newDistrict = new District("ut-1", seed);
    newDistrict.setCandidatePrecincts(this.state.findAdjPrecincts(seed));
    double maxCompactness;
    int precinctVisited;
    try {
      while(counter<100){
        maxCompactness = 0.0;
        Precinct bestPrecinct = null;
        precinctVisited = 0;
        Iterator iterator = newDistrict.getCandidatePrecincts().iterator();
        while (iterator.hasNext()&&precinctVisited<20) {
          Precinct p = (Precinct) iterator.next();
          newDistrict.addPrecinct(p);
          Geometry districtShape = newDistrict.getGeometryShape();

          if (calCompactness(districtShape.getArea(), districtShape.getLength()) > maxCompactness) {
            maxCompactness = calCompactness(districtShape.getArea(), districtShape.getLength());
            bestPrecinct = p;
          }
          newDistrict.removePrecinct(p);
          precinctVisited++;
        }
       
        System.out.println("in loop "+counter +" the best precinct is"+ bestPrecinct.getName());
        newDistrict.addPrecinct(bestPrecinct);
        newDistrict.getCandidatePrecincts().addAll(this.state.findAdjPrecincts(bestPrecinct));
        newDistrict.setCandidatePrecincts(newDistrict.getCandidatePrecincts());
        counter++;
      }
      GeoJSONWriter writer = new GeoJSONWriter();
      GeoJSON json = writer.write(newDistrict.getGeometryShape());
      System.out.println(json.toString());
      
    } catch (Exception ex) {
     System.out.println(ex.getMessage());
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
