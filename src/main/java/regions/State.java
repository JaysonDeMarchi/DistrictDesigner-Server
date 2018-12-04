package regions;

import enums.ShortName;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Hengqi Zhu
 */
@Entity
@Table(name = "STATE")
public class State implements Serializable {

  private String id;
  private String name;
  private ShortName shortName;

  public State(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public State(ShortName shortName) {
    this.shortName = shortName;
  }

  public State() {
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

  @Column(name = "NAME")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Transient
  public ShortName getShortName() {
    return this.shortName;
  }

  public void setShortName(ShortName shortName) {
    this.shortName = shortName;
  }
}
