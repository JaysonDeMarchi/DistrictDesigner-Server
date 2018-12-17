package algorithms;

import enums.Metric;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTWriter;
import managers.UpdateManager;
import regions.District;
import regions.Precinct;

/**
* @author Jayson
*/
public class RegionGrowing extends Algorithm {

  List<Precinct> seeds;

  public RegionGrowing(ShortName shortName, Map<Metric, Float> weights) throws Exception {
    super(shortName, weights);
    this.state.setDistricts(new ArrayList<>());
    this.state.getPrecincts().forEach((precinct) -> precinct.setDistrictId(""));
    start();
  }

  @Override
  public Boolean start() {
    this.setSeedsRandomly(2);
    int districtNameIndex = 0;
    ArrayList<District> newDistricts = new ArrayList<>();
    for(Precinct p : this.seeds){
      District newDistrict = new District(Integer.toString(districtNameIndex++),p);
      p.setMovable(false);
      newDistrict.setCandidatePrecincts(this.state.findAdjPrecincts(p));
      newDistricts.add(newDistrict);
    }
    
    int counter = 0;
    while(counter<50){
      for(District d: newDistricts){
        if(d.getPopulation()>=this.state.getPopulation()/2){
          continue;
        }else{
          districtGrowing(d,newDistricts);
        }
      }
      counter++;
    }
    WKTWriter wktWriter = new WKTWriter();
    for(District d: newDistricts){
        System.out.println(wktWriter.write(d.getGeometryShape())+"population is "+d.getPopulation());
        System.out.println("democratic got "+d.getPartyResult().get("democratic"));
        System.out.println("republican got "+d.getPartyResult().get("republican"));
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

  public void setSeedsRandomly(int n) {
    this.seeds = new ArrayList<>();
    for (int i=0;i<n;i++) {
      this.seeds.add(((List<Precinct>) (List) this.state.getPrecincts()).get(new Random().nextInt(this.state.getPrecincts().size())));
    }
  }

  private double calCompactness(double area, double perimeter) {
    double r = Math.sqrt(area / Math.PI);
    double equalAreaPerimeter = 2 * Math.PI * r;
    double score = 1 / (perimeter / equalAreaPerimeter);
    return score;
  }
  
  private double calPopulationEqualty(ArrayList<District> districts){
    District max = districts.stream().max(Comparator.comparing(District::getPopulation)).get();
    District min = districts.stream().min(Comparator.comparing(District::getPopulation)).get();
    return 1-(max.getPopulation()-min.getPopulation())/(this.state.getPopulation()/districts.size());
  }
  
  private double calEfficiencyGap(District district){
    //Formula 1 = Wasted of won party = ticketsOfWonParty - ticketsOfLostparty
    //Formula 2 = Wasted of lost party = ticketsOfLostParty
    //|1-2|/total
  
    return 0.0;
  }

  private void districtGrowing(District newDistrict,ArrayList<District> districts){
    double maxObjFunction;
    int precinctVisited;
    try {
        maxObjFunction = 0.0;
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
          double tempObjFunction  =  calCompactness(districtShape.getArea(), districtShape.getLength())+calPopulationEqualty(districts);
          if (tempObjFunction > maxObjFunction) {
            maxObjFunction = tempObjFunction;
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
