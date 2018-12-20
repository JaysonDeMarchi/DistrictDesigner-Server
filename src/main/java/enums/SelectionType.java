package enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import regions.Precinct;
import regions.State;

/**
 *
 * @author Jayson
 */
public enum SelectionType {
  BEST_FIT,
  RANDOM,
  WORST_FIT;

  public Collection<Precinct> createInitialSeeds(Integer numOfDistricts, State state) {
    System.out.println("Creating Seeds List");
    ArrayList<Precinct> seeds = new ArrayList();
    System.out.println("Create Seeds List");
    for (int i = 0; i < numOfDistricts; i++) {
      seeds.add(((ArrayList<Precinct>) state.getPrecincts()).get(new Random().nextInt(state.getPrecincts().size())));
    }
    return seeds;
  }
}
