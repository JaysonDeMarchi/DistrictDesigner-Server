package enums;

import regions.Region;

/**
 *
 * @author Jayson
 */
public enum PropertyAttribute {
  GEOID10 {
    @Override
    public String getValue(Region region) {
      return region.getId();
    }
  };

  public String getValue(Region region) {
    return "";
  }
}
