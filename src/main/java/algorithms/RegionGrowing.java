package algorithms;

import com.vividsolutions.jts.geom.Geometry;
import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import managers.UpdateManager;
import regions.District;
import regions.Precinct;

/**
 *
 * @author Jayson
 */
public class RegionGrowing extends Algorithm {

  private Collection<Precinct> seeds;
  private Collection<District> generatedDistricts;

  public RegionGrowing(Integer numOfDistricts, SelectionType selectionType, ShortName shortName, Map<Metric, Float> weights) {
    super(selectionType, shortName, weights);
    this.generatedDistricts = Stream.generate(District::new)
            .limit(numOfDistricts)
            .collect(Collectors.toList());
    this.seeds = createSeeds();
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

  public Collection<Precinct> createSeeds() {
    Collection<Precinct> seeds = new ArrayList<>();
    Collection<Precinct> allPrecincts = this.getState().getPrecincts();

    switch (this.getSelectionType()) {
      case BEST_FIT:
        break;
      case RANDOM:
        Stack<Integer> indexes = (Stack<Integer>) this.getUniqueRandomIndexes(allPrecincts, this.getGeneratedDistricts().size());
        this.getGeneratedDistricts().forEach((district) -> {
          Precinct precinct = ((ArrayList<Precinct>) allPrecincts).get(indexes.pop());
          seeds.add(precinct);
          district.addPrecinct(precinct);
        });
        break;
      case WORST_FIT:
        break;
      default:
        return null;
    }
    return seeds;
  }

  private Collection<District> getGeneratedDistricts() {
    return this.generatedDistricts;
  }

  private Collection<Integer> getUniqueRandomIndexes(Collection<Precinct> precincts, Integer maxSize) {
    Random random = new Random();
    Set<Integer> generated = new LinkedHashSet<>();
    while (generated.size() < maxSize) {
      Integer index = random.nextInt(precincts.size());
      generated.add(index);
    }
    return generated;
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
    districts.forEach((d) -> {
      this.seeds.add(((List<Precinct>) (List) d.getPrecincts()).get(new Random().nextInt(d.getPrecincts().size())));
    });
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
