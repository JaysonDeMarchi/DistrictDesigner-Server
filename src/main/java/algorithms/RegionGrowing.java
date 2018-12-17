package algorithms;

import enums.Metric;
import enums.ShortName;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.locationtech.jts.io.WKTWriter;
import managers.UpdateManager;
import regions.District;
import regions.Precinct;

/**
* @author Jayson
*/
public class RegionGrowing extends Algorithm {

  List<Precinct> seeds;

  public RegionGrowing(ShortName shortName, EnumMap<Metric, Float> weights) throws Exception {
    super(shortName, weights);
    this.state.setDistricts(new ArrayList<>());
    this.state.getPrecincts().forEach((precinct) -> precinct.setDistrictId(""));
  }

  @Override
  public Boolean start() {
  
    
    return true;
  }

  @Override
  public UpdateManager run() {
    while(!this.getUpdateManager().isReady()) {
      this.setSeedsRandomly(5);
      int districtNameIndex = 0;
      for(Precinct p : this.seeds){
        District newDistrict = new District(Integer.toString(districtNameIndex++),p);
        p.setMovable(false);
        newDistrict.setCandidatePrecincts(this.state.findAdjPrecincts(p));
        this.state.getDistricts().add(newDistrict);
      }
    
      int counter = 0;
      while(counter<50){
        for(District d: this.state.getDistricts()){
          if(d.getPopulation()>=this.state.getPopulation()/this.state.getDistricts().size()){
            continue;
          }else{
            districtGrowing(d, (ArrayList<District>) this.state.getDistricts());
          }
        }
        counter++;
      }
      WKTWriter wktWriter = new WKTWriter();
      for(District d: this.state.getDistricts()){
          System.out.println(wktWriter.write(d.getGeometryShape())+"population is "+d.getPopulation());
      }
    }
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

//          double tempObjFunction  =  calCompactness(districtShape.getArea(), districtShape.getLength())+calPopulationEqualty(districts);
          Double tempObjFunction = newDistrict.calculateObjectiveFunction(this.weights);
          if (tempObjFunction > maxObjFunction) {
            maxObjFunction = tempObjFunction;
            bestPrecinct = p;
          }
          newDistrict.removePrecinct(p);
          precinctVisited++;
        }
        newDistrict.addPrecinct(bestPrecinct);
        bestPrecinct.setMovable(false);
        System.out.println("the best precinct is "+ bestPrecinct.getName());
        newDistrict.getCandidatePrecincts().addAll(this.state.findAdjPrecincts(bestPrecinct));
        newDistrict.setCandidatePrecincts(newDistrict.getCandidatePrecincts());
    } catch (Exception ex) {
     System.out.println(ex.getMessage());
    }
  }

}
