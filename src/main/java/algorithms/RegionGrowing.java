package algorithms;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import enums.Metric;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import metrics.Schwartzberg;
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
    //JTS configuration 
    GeometryFactory geometryFactory = new GeometryFactory();
    WKTReader reader = new WKTReader(geometryFactory);
    WKTWriter writer = new WKTWriter();
    //Compactness calculator
    Schwartzberg szg = new Schwartzberg();
    
    //every time add a precinct to the district, newDistrictBoundary grows
    HashSet<Geometry> newDistrictBoundary = new HashSet<>();
    //every time add a precinct to the district, also add it's adjPrecncts(ID) to this list as candidates
    HashSet<Precinct> candidatePrecincts = new HashSet<>();

    //get a seed to start
    this.setSeedsRandomly(this.districts);
    Precinct p1 = this.seeds.get(0);
    System.out.println("seed is "+ p1.getBoundary());

    //create a district, put the seed into it, hard code
    District newDistrict = new District("UT-1", p1.getBoundary(), this.state.getShortName().toString());
    newDistrict.addPrecincts(p1);
//    newDistrict.setArea(p1.getLandArea()+p1.getWaterArea());

    try {
      //initialize newDistrictBoundary and candidatePrecincts
      newDistrictBoundary.add(reader.read(p1.getBoundary()));
      for (String adjPrecinctId : stringToList(p1.getAdjPrecincts())) {
        candidatePrecincts.add(findPrecinctById(adjPrecinctId, this.precincts));
      }

      double  compactnessMax = 0.0;
      Precinct bestPrecinct = null;
      //print length of (an adjacent precinct + district(seed))
      //so we can find the best metric (area known)
      int i = 0;
      Iterator iterator = candidatePrecincts.iterator();
      while(iterator.hasNext()&&i<10) {
        Precinct p = (Precinct) iterator.next();
        //put them into district
        Geometry precintBoundary = reader.read(p.getBoundary());
        newDistrictBoundary.add(precintBoundary);
        GeometryCollection combinDistPricint =  (GeometryCollection) geometryFactory.buildGeometry(newDistrictBoundary);
        Geometry tempDistrict = combinDistPricint.union();
        
        if (szg.calculate(tempDistrict.getArea() ,tempDistrict.getLength())>compactnessMax){
          bestPrecinct = p;
        }
        //
        newDistrictBoundary.remove(precintBoundary);
      }
      
      //put it in an outter loop
//        {
//          newDistrict.addPrecincts(p);
//          newDistrictBoundary.add(precintBoundary);
//          for (String adjPrecinctId : stringToList(p.getAdjPrecincts())) {
//            candidatePrecincts.add(findPrecinctById(adjPrecinctId,this.precincts));
//          }
//          candidatePrecincts.removeAll(newDistrict.getPrecincts());                 
//          iterator = candidatePrecincts.iterator();
//          compactnessMax = 0;
//          i++;
//        }
      //put it in an outter loop
      
      Geometry combinDistPricint =  geometryFactory.buildGeometry(newDistrictBoundary);
      System.out.println("final map is "+writer.write(combinDistPricint.union()));  

      System.out.println(candidatePrecincts.size());
    } catch (ParseException ex) {
      System.out.println(ex);
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
    int i=0;
    for (District d : districts) {
      this.seeds.add(((List<Precinct>) (List) d.getPrecincts()).get(i+=100));
    }
  }

  public Collection<Precinct> getSeeds() {
    return this.seeds;
  }

}
