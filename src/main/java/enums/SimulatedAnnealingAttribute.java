package enums;

/**
 *
 * @author Jayson
 */
public enum SimulatedAnnealingAttribute {
  STRICTNESS(1000.0),
  INCREASED_STRICTNESS_RATE(0.3),
  EXIT_THRESHOLD(1.0);

  private final Double value;

  private SimulatedAnnealingAttribute(Double value) {
    this.value = value;
  }

  public Double getValue() {
    return this.value;
  }
}
