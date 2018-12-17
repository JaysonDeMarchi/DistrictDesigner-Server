package enums;

/**
 *
 * @author Jayson
 */
public enum RequestParam {
  SHORT_NAME("shortName");

  private String name;

  private RequestParam(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
