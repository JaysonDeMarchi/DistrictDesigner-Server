package algorithms;

import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import managers.UpdateManager;
import moves.Addition;
import regions.District;
import regions.Precinct;

/**
 * @author Jayson
 */
public class RegionGrowing extends Algorithm {

  private List<Precinct> seeds;
  private HashSet<District> growableDistricts;

  public RegionGrowing(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights, Integer numOfDistricts) throws Exception {
    super(shortName, selectionType, weights);
    this.state.getPrecincts().forEach((precinct) -> precinct.setDistrictId(""));
    this.setSeedsRandomly(numOfDistricts);
    int districtNameIndex = 0;
    for (Precinct p : this.seeds) {
      District newDistrict = new District(this.state.getShortName() + Integer.toString(districtNameIndex++), p);
      newDistrict.setCandidatePrecincts(this.state.findAdjPrecincts(p));
      this.state.getDistricts().add(newDistrict);
    }

    this.growableDistricts = new HashSet<>();
    this.growableDistricts.addAll(this.getState().getDistricts());

  }

  @Override
  public Boolean start() {
    return true;
  }

  @Override
  public UpdateManager run() {
    while (!this.getUpdateManager().isReady()) {
      System.out.println("Update Manager: " + this.getUpdateManager().getCurrentSize());
      Integer precinctsInDistricts = sumOfPrecincts(this.getState().getDistricts());
      while (this.getState().getPrecincts().size() > precinctsInDistricts && !this.getGrowableDistricts().isEmpty()) {
        District minPopDistrict = this.getGrowableDistricts().stream().min(Comparator.comparing(District::getPopulation)).get();
        if (!minPopDistrict.getCandidatePrecincts().isEmpty()) {
          districtGrowing(minPopDistrict, (ArrayList) this.getState().getDistricts());
        } else {
          this.getGrowableDistricts().remove(minPopDistrict);
        }
        precinctsInDistricts = sumOfPrecincts(this.getState().getDistricts());
      }
    }
    return this.getUpdateManager();
  }

  public void setSeedsRandomly(int n) {
    this.seeds = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      this.seeds.add(((List<Precinct>) (List) this.state.getPrecincts()).get(new Random().nextInt(this.state.getPrecincts().size())));
    }
  }

  private void districtGrowing(District targetDistrict, ArrayList<District> districts) {
    try {
      Double maxObjFunction = 0.0;
      Precinct bestPrecinct = null;
      for (Precinct p : targetDistrict.getCandidatePrecincts()) {
        targetDistrict.addPrecinct(p);
        Double tempObjFunction = targetDistrict.calculateObjectiveFunction(this.weights);
        if (tempObjFunction > maxObjFunction) {
          maxObjFunction = tempObjFunction;
          bestPrecinct = p;
        }
        targetDistrict.removePrecinct(p);
      }
      targetDistrict.addPrecinct(bestPrecinct);
      this.getUpdateManager().addAddition(new Addition(bestPrecinct, targetDistrict, true));
      HashSet<Precinct> bestPrecinctAdj = (HashSet<Precinct>) this.state.findAdjPrecincts(bestPrecinct).stream().filter(precinct -> !precinct.getDistrictId().equals("")).collect(Collectors.toSet());
      targetDistrict.getCandidatePrecincts().addAll(bestPrecinctAdj);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private int sumOfPrecincts(Collection<District> districts) {
    int numOfprecints = 0;
    numOfprecints = districts.stream().map((d) -> d.getPrecincts().size()).reduce(numOfprecints, Integer::sum);
    return numOfprecints;
  }

  private HashSet<District> getGrowableDistricts() {
    return this.growableDistricts;
  }

  private void setGrowableDistricts(HashSet<District> growableDistricts) {
    this.growableDistricts = growableDistricts;
  }
}
