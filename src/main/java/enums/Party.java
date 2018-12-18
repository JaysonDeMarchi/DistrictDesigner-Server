package enums;

/**
 *
 * @author hqzhu
 */
public enum Party {
  DEMOCRATIC("democratic"),
  REPUBLICAN("republican");
  
  private String name;
  
  private Party(String name) {
    this.name = name;
  }
  
  @Override
  public String toString() {
    return this.name;
  }
}
