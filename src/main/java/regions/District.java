package regions;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Hengqi
 */
@Entity
@Table(name = "DISTRICT")
public class District implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String boundaryJSON;
    private String state;
    Collection<Precinct> precincts;

    public District(){}
    
    public District(String id, String boundaryJSON, String state) {
        this.id = id;
        this.boundaryJSON = boundaryJSON;
        this.state = state;
    }

    @Id @GeneratedValue
    @Column(name = "ID")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "BOUNDARY")
    public String getBoundaryJSON() {
        return this.boundaryJSON;
    }

    public void setBoundaryJSON(String boundaryJSON) {
        this.boundaryJSON = boundaryJSON;
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
     
}
