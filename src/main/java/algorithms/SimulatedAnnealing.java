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
    return this.getUpdateManager();
  }
}
