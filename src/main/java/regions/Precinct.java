package regions;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import utils.Validator;

/**
 *
 * @author Hengqi Zhu
 */
@Entity
@Table(name="PRECINCT")
public class Precinct implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;	
    private String boundaryJSON;
    private String state;
    private String districtId;
    private String adjPrecincts;

    public Precinct() {}

    public Precinct(String precinctId, String districtId, String boundaryJSON, String adjPrecincts) throws Exception {
            this.id = precinctId;
            if(!Validator.isJSONValid(boundaryJSON))
                    throw new Exception("boundaryJSON value is not a valid JSON");		
            this.boundaryJSON = boundaryJSON;
            this.districtId = districtId;
            if(!Validator.isJSONValid(adjPrecincts))
                    throw new Exception("boundaryJSON value is not a valid JSON");
            this.adjPrecincts = adjPrecincts;
    }

    @Id @GeneratedValue
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

    @Column(name = "BOUNDARY")
    public String getBoundaryJSON() {
            return this.boundaryJSON;
    }

    public void setboundaryJSON(String boundaryJSON) throws Exception {
            if(!Validator.isJSONValid(boundaryJSON))
                    throw new Exception("boundaryJSON value is not a valid JSON");
            this.boundaryJSON = boundaryJSON;
    }

    @Column(name = "DISTRICT")
    public String getDistrictId() {
            return this.districtId;
    }

    public void setDistrictId(String districtId) {
            this.districtId = districtId;
    }

    @Column(name = "ADJPRECINCTS")
    public String getAdjPrecincts() {
        return this.adjPrecincts;
    }

    public void setAdjPrecincts(String adjPrecincts) {
        this.adjPrecincts = adjPrecincts;
    }

    @Column(name = "STATE")
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

}


