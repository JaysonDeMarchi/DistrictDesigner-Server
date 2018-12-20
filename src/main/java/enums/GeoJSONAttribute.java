package enums;

/**
 *
 * @author hqzhu
 */
public enum GeoJSONAttribute {
    GEOMETRY("geometry"),
    PROPERTIES("properties");
    
    private String name;
    
    private GeoJSONAttribute(String name) {
      this.name = name;
    }
    
    public String toString() {
      return this.name;
    }
}
