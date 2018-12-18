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
  RANDOM {
    @Override
    public Move movePrecinct(State state) {
      Random rand = new Random();
      District newDistrict = (District) ((ArrayList) state.getDistricts()).get(rand.nextInt(state.getDistricts().size()));
      Set<Precinct> edgePrecincts = newDistrict.getEdgePrecincts();
      Precinct chosenPrecinct;
      District oldDistrict;
      for (Precinct precinct : edgePrecincts) {
        List<Precinct> validPrecincts = precinct.getAdjPrecincts().stream().filter((adjPrecinct) -> !precinct.getDistrictId().equals(adjPrecinct.getDistrictId())).collect(Collectors.toList());
        if (!validPrecincts.isEmpty()) {
          chosenPrecinct = validPrecincts.get(rand.nextInt(validPrecincts.size()));
          oldDistrict = state.getDistrictById(chosenPrecinct.getDistrictId());
          return new Move(chosenPrecinct, oldDistrict, newDistrict);
        }
      }
      return null;
    }
  },
  WORST_FIT;

  public Move movePrecinct(State state) {
    return null;
  }
;
}
