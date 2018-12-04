package regions;

import java.util.Collection;

/**
 *
 * @author Jayson
 */
public class District {

  private Collection<Precinct> edgePrecincts;
  private Collection<Precinct> precincts;

  public District() {

  }

  public double getArea() {
    return this.getPrecincts().stream().map(Precinct::getArea)
            .reduce(0.0, (precinctAreaA, precinctAreaB) -> precinctAreaA + precinctAreaB);
  }

  public double getPerimeter() {
    return this.getEdgePrecincts().stream()
            .map(precinct -> precinct.getEdges()
            .filter(edge -> !edge.getHeadDistrict().equals(edge.getTailDistrict()))
            .map(edge -> edge.getWidth()))
            .reduce(0.0, (perimeterSegmentA, perimeterSegmentB) -> perimeterSegmentA + perimeterSegmentB);
  }

  private Collection<Precinct> getEdgePrecincts() {
    return this.edgePrecincts;
  }

  private Collection<Precinct> getPrecincts() {
    return this.precincts;
  }
}
