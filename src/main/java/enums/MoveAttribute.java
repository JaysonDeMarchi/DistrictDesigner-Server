package enums;

/**
 *
 * @author Jayson
 */
public enum MoveAttribute {
  SIZE_ON_FAILURE(1),
  SIZE_ON_SUCCESS(3),
  MAX_CAPACITY(20);

  Integer value;

  private MoveAttribute(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return this.value;
  }
}
