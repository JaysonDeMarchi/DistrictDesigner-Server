package regions;

import java.util.Collection;

/**
 *
 * @author Jayson
 */
public class Precinct {

  private Double area;
  private Collection<Edge> edges;

  public Precinct() {

  }

  public Double getArea() {
    return this.area;
  }

  public Collection<Edge> getEdges() {
    return this.edges;
  }
}
