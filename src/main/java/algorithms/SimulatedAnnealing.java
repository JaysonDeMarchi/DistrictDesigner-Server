package algorithms;

import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import enums.SimulatedAnnealingAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import managers.UpdateManager;
import moves.Move;
import regions.District;
import regions.State;

/**
 *
 * @author Jayson
 */
public class SimulatedAnnealing extends Algorithm {

  private State bestState;
  private Double strictness;
  private List<Move> moveCache;

  public SimulatedAnnealing(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights) throws Exception {
    super(shortName, selectionType, weights);
    this.getState().setDistricts(this.cloneDistricts(this.getOriginalState()));
    this.strictness = SimulatedAnnealingAttribute.STRICTNESS.getValue();
    this.bestState = this.getState().clone();
    this.bestState.setDistricts(cloneDistricts(this.getState()));
  }

  @Override
  public Boolean start() {
    return true;
  }

  @Override
  public UpdateManager run() {
    System.out.println("Update Manager is :" + this.getUpdateManager().isReady());
    while (!this.getUpdateManager().isReady()) {
      System.out.println("Compare strictness to exit threshold");
      if (this.getStrictness() < SimulatedAnnealingAttribute.EXIT_THRESHOLD.getValue()) {
        return this.end();
      }
      try {
        System.out.println("Create new State");
        State newState = this.getState().clone();
        newState.setDistricts(this.cloneDistricts(this.getState()));

        System.out.println("Create a move");
        Move move = SelectionType.RANDOM.movePrecinct(newState);

        System.out.println("Compare newState and State objective functions");
        if (moveAccepted(this.getState().calculateObjectiveFunction(this.getWeights()),
                newState.calculateObjectiveFunction(this.getWeights()))) {
          System.out.println("Replace State with newState");
          this.setState(newState);
        }
        System.out.println("New state obj func");
        newState.calculateObjectiveFunction(this.getWeights());
        System.out.println("Best state obj func");
        this.getBestState().calculateObjectiveFunction(this.getWeights());
        System.out.println("Move: ");
        System.out.println("Set the move success by comparing newState to best state");
        move.setSuccessStatus(newState.calculateObjectiveFunction(this.getWeights()) > this.getBestState().calculateObjectiveFunction(this.getWeights()));
        System.out.println("Check if the move was better");
        if (move.getSuccessStatus()) {
          System.out.println("Replace the best state with the newState");
          this.setBestState(newState.clone());
          this.getBestState().setDistricts(this.cloneDistricts(newState));
        }
        System.out.println("Add the move to the update manager");
        this.getUpdateManager().addMove(move);
        System.out.println("Adjust the strictness");
        this.setStrictness(this.getStrictness() * SimulatedAnnealingAttribute.INCREASED_STRICTNESS_RATE.getValue());
      } catch (CloneNotSupportedException e) {
      }
    }
    return this.getUpdateManager();
  }

  @Override
  public UpdateManager end() {
    this.getUpdateManager().setComplete(true);
    return this.getUpdateManager();
  }

  public Collection<District> cloneDistricts(State state) {
    Collection<District> districtClones = new ArrayList<>();
    state.getDistricts().forEach((d) -> {
      try {
        districtClones.add(d.clone());
      } catch (CloneNotSupportedException ex) {
      }
    });
    return districtClones;
  }

  public Boolean moveAccepted(Double originalObjFunc, Double newObjFunc) {
    if (originalObjFunc < newObjFunc) {
      return true;
    }
    Random rand = new Random();
    return ((originalObjFunc - newObjFunc) / this.getStrictness()) > rand.nextDouble();
  }

  private State getBestState() {
    return this.bestState;
  }

  private void setBestState(State state) {
    this.bestState = state;
  }

  private Double getStrictness() {
    return this.strictness;
  }

  private void setStrictness(Double strictness) {
    this.strictness = strictness;
  }

  private List<Move> getMoveCache() {
    return this.moveCache;
  }

  private void setMoveCache(List<Move> moveCache) {
    this.moveCache = moveCache;
  }

  private void clearMoveCache() {
    this.setMoveCache(new ArrayList<>());
  }
}
