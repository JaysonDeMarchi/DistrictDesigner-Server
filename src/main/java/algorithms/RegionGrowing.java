package algorithms;


import enums.Metric;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTWriter;
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
    this.setSeedsRandomly(8);
    int districtNameIndex = 0;
    List<District> newDistricts = new ArrayList<>();
    for(Precinct p : this.seeds){
      District newDistrict = new District(Integer.toString(districtNameIndex++),p);
      p.setMovable(false);
      newDistrict.setCandidatePrecincts(this.state.findAdjPrecincts(p));
      newDistricts.add(newDistrict);
    }
    
    int counter = 0;
    while(counter<50){
      for(District d: newDistricts){
        districtGrowing(d);
      }
      counter++;
    }
    
    WKTWriter wktWriter = new WKTWriter();
    
    for(District d: newDistricts){
        System.out.println(wktWriter.write(d.getGeometryShape())+"population is "+d.getPopulation());
    }

    return true;
  }
  
  /**
   * This is used to get precincts randomly to start region growing according to
   * existing districts.
   *
   * @param districts
   */

  public void setSeedsRandomly(int n) {
    this.seeds = new ArrayList<>();
    for (int i=0;i<n;i++) {
      this.seeds.add(((List<Precinct>) (List) this.state.getPrecincts()).get(new Random().nextInt(this.state.getPrecincts().size())));
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

  
  private void districtGrowing(District newDistrict){
    double maxCompactness;
    int precinctVisited;
    try {
        maxCompactness = 0.0;
        Precinct bestPrecinct = null;
        precinctVisited = 0;
        Iterator iterator = newDistrict.getCandidatePrecincts().iterator();
        while (iterator.hasNext()&&precinctVisited<20) {
          Precinct p = (Precinct) iterator.next();
          if(!p.isMovable()){
            continue;
          }
          newDistrict.addPrecinct(p);
          Geometry districtShape = newDistrict.getGeometryShape();

          if (calCompactness(districtShape.getArea(), districtShape.getLength()) > maxCompactness) {
            maxCompactness = calCompactness(districtShape.getArea(), districtShape.getLength());
            bestPrecinct = p;
          }
          newDistrict.removePrecinct(p);
          precinctVisited++;
        }

        newDistrict.addPrecinct(bestPrecinct);
        bestPrecinct.setMovable(false);
        newDistrict.getCandidatePrecincts().addAll(this.state.findAdjPrecincts(bestPrecinct));
        newDistrict.setCandidatePrecincts(newDistrict.getCandidatePrecincts());
    } catch (Exception ex) {
     System.out.println(ex.getMessage());
    }
  }

}
