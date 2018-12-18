package algorithms;

import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import enums.SimulatedAnnealingAttribute;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;
import managers.UpdateManager;
import moves.Move;
import regions.State;

/**
 *
 * @author Jayson
 */
public class SimulatedAnnealing extends Algorithm {

  private State bestState;
  private Double strictness;
  private List<Move> moveCache;

  public SimulatedAnnealing(ShortName shortName, EnumMap<Metric, Float> weights) throws Exception {
    super(shortName, weights);
    this.bestState = this.state;
    this.strictness = SimulatedAnnealingAttribute.STRICTNESS.getValue();
    this.moveCache = new ArrayList<>();
  }

  @Override
  public Boolean start() {
    return true;
  }

  @Override
  public UpdateManager run() {
    while (!this.getUpdateManager().isReady()) {
      if (this.getStrictness() < SimulatedAnnealingAttribute.EXIT_THRESHOLD.getValue()) {
        return this.end();
      }

      State newState = new State(this.getState());
      Move move = SelectionType.RANDOM.movePrecinct(newState);
      if (moveAccepted(this.getState().calculateObjectiveFunction(this.getWeights()),
              newState.calculateObjectiveFunction(this.getWeights())));
      move.setSuccessStatus(newState.getObjectiveFunction() > this.getBestState().getObjectiveFunction());
      if (move.getSuccessStatus()) {
        this.setBestState(new State(newState));
      }
      this.getUpdateManager().addMove(move);
      this.setStrictness(this.getStrictness() * SimulatedAnnealingAttribute.INCREASED_STRICTNESS_RATE.getValue());
    }
    return this.getUpdateManager();
  }

  public Boolean moveAccepted(Double originalObjFunc, Double newObjFunc) {
    if (originalObjFunc < newObjFunc) {
      return true;
    }
    Random rand = new Random();
    return ((originalObjFunc - newObjFunc) / this.getStrictness()) > rand.nextDouble();
  }

  @Override
  public UpdateManager end() {
    return this.getUpdateManager();
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
