package algorithms;

import enums.Metric;
import enums.Party;
import enums.SelectionType;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.locationtech.jts.io.WKTWriter;
import managers.UpdateManager;
import org.locationtech.jts.geom.MultiPolygon;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;
import regions.District;
import regions.Precinct;

/**
* @author Jayson
*/
public class RegionGrowing extends Algorithm {

  List<Precinct> seeds;
  GeoJSONReader reader;

  public RegionGrowing(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights, Integer numOfDistricts) throws Exception {
    super(shortName, selectionType, weights);
    this.state.setDistricts(new ArrayList<>());
    this.state.getPrecincts().forEach((precinct) -> precinct.setDistrictId(""));
    reader = new GeoJSONReader();
    run();
  }

  @Override
  public Boolean start() {
    return true;
  }

  @Override
  public UpdateManager run() {
//    while(!this.getUpdateManager().isReady()) {
      GeoJSONWriter geojsonWriter = new GeoJSONWriter();
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
      int limit = 0;
      while(this.getState().getPrecincts().size() > precinctIndistrict&&!toGrowDistricts.isEmpty()&&limit<1000){
        District minPopDistrict = toGrowDistricts.stream().min(Comparator.comparing(District::getPopulation)).get();
        if(!minPopDistrict.getCandidatePrecincts().isEmpty()){
          districtGrowing(minPopDistrict, (ArrayList<District>) this.state.getDistricts());
        }else{
          toGrowDistricts.remove(minPopDistrict);
        }
        precinctIndistrict = precinctInDistrict(this.state.getDistricts());
        System.out.println(precinctIndistrict+" precincts finished");
        limit++;
      }
      
      for (District d : this.state.getDistricts()){
        System.out.println("polygon of "+d.getId()+" is "+geojsonWriter.write(d.getGeometryShape()));
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
        maxObjFunction = 0.0;
        int precinctVisited = 0;
        Precinct bestPrecinct = null;
        Iterator iterator = newDistrict.getCandidatePrecincts().iterator();
        while (iterator.hasNext()&&precinctVisited<5) {
          Precinct p = (Precinct) iterator.next();
          if(newDistrict.getGeometryShape().contains(this.reader.read(p.getBoundary()))) {
            newDistrict.addPrecinct(p);
            HashSet<Precinct> bestPrecinctAdj = (HashSet<Precinct>) this.state.findAdjPrecincts(p);
            newDistrict.getCandidatePrecincts().addAll(bestPrecinctAdj);
            newDistrict.setCandidatePrecincts(newDistrict.getCandidatePrecincts());
            return;
          }
          newDistrict.addPrecinct(p);
          if(newDistrict.getGeometryShape() instanceof MultiPolygon){
            newDistrict.removePrecinct(p);
            continue;
          }
          Double tempObjFunction = newDistrict.calculateObjectiveFunction(this.weights);
          if (tempObjFunction > maxObjFunction) {
            maxObjFunction = tempObjFunction;
            bestPrecinct = p;
          }
          newDistrict.removePrecinct(p);
          precinctVisited++;
        }
        newDistrict.addPrecinct(bestPrecinct);
        HashSet<Precinct> bestPrecinctAdj = (HashSet<Precinct>) this.state.findAdjPrecincts(bestPrecinct);
        newDistrict.getCandidatePrecincts().addAll(bestPrecinctAdj);
        newDistrict.setCandidatePrecincts(newDistrict.getCandidatePrecincts());
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
