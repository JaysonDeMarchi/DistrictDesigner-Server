package beans;

import java.util.Map;
import enums.ShortName;
import enums.AlgorithmType;
import enums.Metric;
import java.util.EnumMap;

/**
 *
 * @author Jayson
 */
public class StartRequestParams {

  private AlgorithmType algoType;
  private ShortName shortName;
  private EnumMap<Metric, Float> weights;

  public StartRequestParams() {
    super();
  }

  public StartRequestParams(AlgorithmType a, ShortName s, EnumMap<Metric, Float>  w) {
    this.algoType = a;
    this.shortName = s;
    this.weights = w;
  }

  public AlgorithmType getAlgoType() {
    return this.algoType;
  }

  public ShortName getShortName() {
    return this.shortName;
  }

  public EnumMap<Metric, Float> getWeights() {
    return this.weights;
  }

  public void setAlgoType(AlgorithmType a) {
    this.algoType = a;
  }

  public void setShortName(ShortName s) {
    this.shortName = s;
  }

  public void setWeights(EnumMap<Metric, Float> w) {
    this.weights = w;
  }

  @Override
  public String toString() {
    return "Start Algorithm Request:"
            + "\n\tAlgorithm: " + this.getAlgoType()
            + "\n\tState: " + this.getShortName()
            + "\n\tWeights: " + this.getWeights();
  }
}
