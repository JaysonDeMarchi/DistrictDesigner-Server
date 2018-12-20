package enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import moves.Move;
import regions.District;
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

  public Move movePrecinct(State state) {
    System.out.println("Moving precinct");
    Random rand = new Random();
    System.out.println("Selecting a new district");
    District newDistrict = (District) ((ArrayList) state.getDistricts()).get(rand.nextInt(state.getDistricts().size()));
    System.out.println("New District: " + newDistrict.getId());
    System.out.println("Getting the districts edge precincts");
    Set<Precinct> edgePrecincts = newDistrict.getEdgePrecincts();
    Precinct chosenPrecinct;
    District oldDistrict;
    System.out.println("Edge precincts: " + edgePrecincts.size());
    for (Precinct precinct : edgePrecincts) {
      List<Precinct> validPrecincts = precinct.getAdjPrecincts().stream().filter((adjPrecinct) -> !precinct.getDistrictId().equals(adjPrecinct.getDistrictId())).collect(Collectors.toList());
      System.out.println("Valid precincts: " + validPrecincts.size());
      if (!validPrecincts.isEmpty()) {
        chosenPrecinct = validPrecincts.get(rand.nextInt(validPrecincts.size()));
        System.out.println("Chosen precinct: " + chosenPrecinct.getName());
        oldDistrict = state.getDistrictById(chosenPrecinct.getDistrictId());
        System.out.println("Old District: " + oldDistrict.getId());
        System.out.println("Add Precinct");
        newDistrict.addPrecinct(chosenPrecinct);
        System.out.println("Remove Precinct");
        oldDistrict.removePrecinct(chosenPrecinct);
        return new Move(chosenPrecinct, oldDistrict, newDistrict);
      }
    }
    return null;
  }
}
