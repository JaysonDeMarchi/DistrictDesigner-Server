package regions;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Hengqi
 */
@Entity
@Table(name = "DISTRICT")
public class District implements Serializable {

  private String id;
  private String boundary;
  private String stateName;
  private State state;
  private Collection<Precinct> precincts;
  private Collection<Geometry> geoBoundary;
  GeometryFactory geometryFactory;
  WKTReader reader;

  public District() {
  }

  public District(String id,Precinct p) throws ParseException {
    this.geometryFactory = new GeometryFactory();
    this.reader = new WKTReader();
    this.id = id;
    this.precincts = new HashSet<>();
    this.precincts.add(p);
    this.geoBoundary = new HashSet<>();
    this.geoBoundary.add(reader.read(p.getBoundary()));
  }

  @Id
  @GeneratedValue
  @Column(name = "ID")
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "STATE")
  public String getStateName() {
    return this.stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  @Transient
  public State getState() {
    return this.state;
  }

  public void setState(State state) {
    this.state = state;
  }

  @Column(name = "BOUNDARY")
  public String getBoundary() {
    return this.boundary;
  }

  public void setBoundary(String boundary) {
    this.boundary = boundary;
  }

  @Transient
  public Collection<Precinct> getPrecincts() {
    return this.precincts;
  }

  public void setPrecincts(Collection<Precinct> precincts) {
    this.precincts = precincts;
  }

  public void addPrecinct(Precinct precinct) {
    this.precincts.add(precinct);
  }

  @Transient
  public Collection<Geometry> getGeoBoundary() {
    return geoBoundary;
  }

  public void setGeoBoundary(Collection<Geometry> geoBoundary) {
    this.geoBoundary = geoBoundary;
  }
  
  public void addGeoBoundary(Geometry geometry){
    this.geoBoundary.add(geometry);
  }
  
  public void removeGeoBoundary(Geometry geometry){
    this.geoBoundary.remove(geometry);
  }

  @Transient
  public GeometryFactory getGeometryFactory() {
    return geometryFactory;
  }

  public void setGeometryFactory(GeometryFactory geometryFactory) {
    this.geometryFactory = geometryFactory;
  }

  @Transient
  public WKTReader getReader() {
    return reader;
  }

  public void setReader(WKTReader reader) {
    this.reader = reader;
  }

  
}
