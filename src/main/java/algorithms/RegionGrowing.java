package algorithms;

import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
          district.add(precinct);
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

}
