package beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import enums.ShortName;
import enums.AlgorithmType;
import enums.Metric;
import enums.SelectionType;

/**
 *
 * @author Jayson
 */
public class StartRequestParams {

  private AlgorithmType algoType;
  private ShortName shortName;
  private Map<Metric, Float> weights;
  private Integer numOfDistricts;
  private SelectionType selectionType;

  public StartRequestParams() {
    super();
  }

  public StartRequestParams(AlgorithmType algorithmType, Integer numOfDistricts, SelectionType selectionType, ShortName shortName, Map<Metric, Float> weights) {
    this.algoType = algorithmType;
    this.numOfDistricts = numOfDistricts;
    this.selectionType = selectionType;
    this.shortName = shortName;
    this.weights = weights;
  }

  public AlgorithmType getAlgoType() {
    return this.algoType;
  }

  @JsonProperty
  public Integer getNumOfDistricts() {
    return this.numOfDistricts;
  }

  @JsonProperty
  public SelectionType getSelectionType() {
    return this.selectionType;
  }

  public ShortName getShortName() {
    return this.shortName;
  }

  public Map<Metric, Float> getWeights() {
    return this.weights;
  }

  public void setAlgoType(AlgorithmType a) {
    this.algoType = a;
  }

  public void setNumOfDistricts(Integer n) {
    this.numOfDistricts = n;
  }

  public void setSelectionType(SelectionType s) {
    this.selectionType = s;
  }

  public void setShortName(ShortName s) {
    this.shortName = s;
  }

  public void setWeights(Map<Metric, Float> w) {
    this.weights = w;
  }

  @Override
  public String toString() {
    return "Start Algorithm Request:"
            + "\n\tAlgorithm: " + this.getAlgoType()
            + "\n\tState: " + this.getShortName()
            + "\n\tNumber Of Districts: " + this.getNumOfDistricts()
            + "\n\tSelection Type: " + this.getSelectionType()
            + "\n\tWeights: " + this.getWeights();
  }
}
