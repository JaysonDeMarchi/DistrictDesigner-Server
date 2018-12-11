package regions;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
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
  private String state;
  private long area;
  Collection<Precinct> precincts;

  public District() {}

  public District(String id, String boundary, String state) {
    this.id = id;
    this.boundary = boundary;
    this.state = state;
    this.precincts = new LinkedList<>();
    this.area = 0;
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
  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Transient
  public Collection<Precinct> getPrecincts() {
    return this.precincts;
  }

  public void setPrecincts(Collection<Precinct> precincts) {
    this.precincts = precincts;
  }

  public void addPrecincts(Precinct p) {
    this.precincts.add(p);
  }
  
  @Column(name = "BOUNDARY")
  public String getBoundary() {
    return boundary;
  }

  public void setBoundary(String boundary) {
    this.boundary = boundary;
  }
  
  @Transient
  public long getArea() {
    return area;
  }

  public void setArea(long area) {
    this.area = area;
  }

}
