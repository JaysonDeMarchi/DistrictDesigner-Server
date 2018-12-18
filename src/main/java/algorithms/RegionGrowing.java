package algorithms;

import enums.Metric;
import enums.Party;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
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
    run();
  }

  @Override
  public Boolean start() {
  
    
    return true;
  }

  @Override
  public UpdateManager run() {
//    while(!this.getUpdateManager().isReady()) {
      WKTWriter wktWriter = new WKTWriter();
      this.setSeedsRandomly(5);
      int districtNameIndex = 0;
      for(Precinct p : this.seeds){
        District newDistrict = new District(this.state.getShortName()+Integer.toString(districtNameIndex++),p);
        newDistrict.setCandidatePrecincts(this.state.findAdjPrecincts(p));
        this.state.getDistricts().add(newDistrict);
      }
      
      HashSet<District> toGrowDistricts = new HashSet<>();
      toGrowDistricts.addAll(this.getState().getDistricts());
      
      int precinctIndistrict = precinctInDistrict(this.state.getDistricts());
      while(this.getState().getPrecincts().size() > precinctIndistrict&&!toGrowDistricts.isEmpty()){
        System.out.println("here3");
        District minPopDistrict = toGrowDistricts.stream().min(Comparator.comparing(District::getPopulation)).get();
        System.out.println(minPopDistrict.getId()+"has "+minPopDistrict.getCandidatePrecincts().size()+"candidate precincts");
        if(!minPopDistrict.getCandidatePrecincts().isEmpty()){
          districtGrowing(minPopDistrict, (ArrayList<District>) this.state.getDistricts());
        }else{
          toGrowDistricts.remove(minPopDistrict);
          continue;
        }
        System.out.println(minPopDistrict.getId()+"has "+minPopDistrict.getPopulation());
        precinctIndistrict = precinctInDistrict(this.state.getDistricts());
        System.out.println(precinctIndistrict+"have done");
      }

      for(District d: this.state.getDistricts()){
        System.out.println(wktWriter.write(d.getGeometryShape()));
        System.out.println("Population is "+d.getPopulation());
        System.out.println("democratic got "+d.getPartyResult().get(Party.DEMOCRATIC.toString()));
        System.out.println("republician got "+d.getPartyResult().get(Party.REPUBLICAN.toString()));
      }
//    }
    return this.getUpdateManager();
  }

  public void setSeedsRandomly(int n) {
    this.seeds = new ArrayList<>();
    for (int i=0;i<n;i++) {
      this.seeds.add(((List<Precinct>) (List) this.state.getPrecincts()).get(new Random().nextInt(this.state.getPrecincts().size())));
    }
  }

  private void districtGrowing(District newDistrict,ArrayList<District> districts){
    double maxObjFunction;
    try {
        int precinctVisted = 0;
        maxObjFunction = 0.0;
        Precinct bestPrecinct = null;
        Iterator iterator = newDistrict.getCandidatePrecincts().iterator();
        while (iterator.hasNext()&&precinctVisted<5) {
          Precinct p = (Precinct) iterator.next();
          newDistrict.addPrecinct(p);
          Double tempObjFunction = newDistrict.calculateObjectiveFunction(this.weights);
          if (tempObjFunction > maxObjFunction) {
            maxObjFunction = tempObjFunction;
            bestPrecinct = p;
          }
          newDistrict.removePrecinct(p);
          precinctVisted++;
        }
        System.out.println(bestPrecinct.getName());
        newDistrict.addPrecinct(bestPrecinct);
        HashSet<Precinct> bestPrecinctAdj = (HashSet<Precinct>) this.state.findAdjPrecincts(bestPrecinct);
        System.out.println("neiberhoud number is "+bestPrecinctAdj.size());
        newDistrict.getCandidatePrecincts().addAll(bestPrecinctAdj);
        System.out.println("here1");

        newDistrict.setCandidatePrecincts(newDistrict.getCandidatePrecincts());
        System.out.println("here2");
    } catch (Exception ex) {
     System.out.println(ex.getMessage());
    }
  }
  
  private int precinctInDistrict(Collection<District> districts){
    int numOfprecints = 0;
    for(District d : districts){
      numOfprecints += d.getPrecincts().size();
    }
    return numOfprecints;
  }
}
