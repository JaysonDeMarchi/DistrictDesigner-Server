package enums;

/**
 *
 * @author Jayson
 */
public enum GeoJSONAttribute {
  GEOMETRY("geometry"),
  PROPERTIES("properties");

  private String name;

  private GeoJSONAttribute(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
