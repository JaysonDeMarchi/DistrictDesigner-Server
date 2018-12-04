package regions;

/**
 *
 * @author Jayson
 */
public class Edge {

  private District headDistrict;
  private District tailDistrict;
  private Double width;

  public Edge() {

  }

  public District getHeadDistrict() {
    return this.headDistrict;
  }

  public District getTailDistrict() {
    return this.tailDistrict;
  }

  public Double getWidth() {
    return this.width;
  }
}
