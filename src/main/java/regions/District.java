package regions;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
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
  private Collection<Precinct> candidatePrecincts;
  GeometryFactory geometryFactory;
  WKTReader reader;
  

  public District() {
    this.reader = new WKTReader();
    this.precincts = new HashSet<>();
    this.geoBoundary = new HashSet<>();
  }

  public District(String id,Precinct seed) throws ParseException {
    this.geometryFactory = new GeometryFactory();
    this.reader = new WKTReader();
    this.id = id;
    this.precincts = new HashSet<>();
    this.geoBoundary = new HashSet<>();
    this.candidatePrecincts = new HashSet<>();
    this.addPrecinct(seed);
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
  public Collection<Precinct> getCandidatePrecincts() {
    return candidatePrecincts;
  }

  public void setCandidatePrecincts(Collection<Precinct> candidatePrecincts) {
    this.candidatePrecincts = candidatePrecincts;
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
    try {
      this.geoBoundary.add(this.reader.read(precinct.getBoundary()));
    } catch (ParseException ex) {
      System.out.println(ex.getMessage());
    }
  }
  
  public void removePrecinct(Precinct precinct){
    this.precincts.remove(precinct);
    try {
      this.geoBoundary.remove(this.reader.read(precinct.getBoundary()));
    } catch (ParseException ex) {
      System.out.println(ex.getMessage());
    }
  }

  @Transient
  public Collection<Geometry> getGeoBoundary() {
    return geoBoundary;
  }

  public void setGeoBoundary(Collection<Geometry> geoBoundary) {
    this.geoBoundary = geoBoundary;
  }
  
  @Transient
  public GeometryFactory getGeometryFactory() {
    return geometryFactory;
  }

  public void setGeometryFactory(GeometryFactory geometryFactory) {
    this.geometryFactory = geometryFactory;
  }
  
  @Transient
  public Geometry getGeometryShape(){
     GeometryCollection geometryShape = (GeometryCollection)this.getGeometryFactory().buildGeometry(this.getGeoBoundary());
     return geometryShape.union(); 
  }

  @Transient
  public WKTReader getReader() {
    return reader;
  }

  public void setReader(WKTReader reader) {
    this.reader = reader;
  }
  
}
