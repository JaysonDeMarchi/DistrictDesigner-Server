package algorithms;

import enums.Metric;
import enums.SelectionType;
import enums.ShortName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import managers.UpdateManager;
import regions.District;

/**
 *
 * @author Jayson
 */
public class SimulatedAnnealing extends Algorithm {


  public SimulatedAnnealing(ShortName shortName, SelectionType selectionType, EnumMap<Metric, Float> weights) throws Exception {
    super(shortName, selectionType, weights);

  }

  @Override
  public Boolean start() {
    return true;
  }

  @Override
  public UpdateManager run() {
    while (!this.getUpdateManager().isReady()) {

    }
    return this.getUpdateManager();
  }

  @Override
  public UpdateManager end() {
    this.getUpdateManager().setComplete(true);
    return this.getUpdateManager();
  }
}
