package algorithms;

import enums.Metric;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import regions.District;
import regions.Precinct;

/**
 *
 * @author Jayson
 */
public class RegionGrowing extends Algorithm {

  Collection<Precinct> seeds;

  public RegionGrowing(ShortName shortName, Map<Metric, Float> weights) {
    super(shortName, weights);
    this.start();
  }

  @Override
  public Boolean start() {
    this.setSeedsRandomly(super.getDistricts());
    return true;
  }

  /**
   * This is used to get precincts randomly to start region growing according to
   * existing districts.
   *
   * @param districts. // * @return void
   */
  public void setSeedsRandomly(Collection<District> districts) {
    this.seeds = new ArrayList<>();
    for (District d : districts)
    {
      this.seeds.add(((List<Precinct>) (List) d.getPrecincts()).get(new Random().nextInt(d.getPrecincts().size())));
    }
  }
  
  public Collection<Precinct> getSeeds() {
    return this.seeds;
  }

}
