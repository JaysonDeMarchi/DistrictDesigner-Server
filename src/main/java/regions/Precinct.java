/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
	private String district;
        private String adjPrecincts;
		
	private Precinct(String precinctId, String districtId, String boundaryJSON, String adjPrecincts) throws Exception {
		this.district = precinctId;
		if(!Validator.isJSONValid(boundaryJSON))
			throw new Exception("boundaryJSON value is not a valid JSON");		
		this.boundaryJSON = boundaryJSON;
		this.district = districtId;
                if(!Validator.isJSONValid(adjPrecincts))
			throw new Exception("boundaryJSON value is not a valid JSON");
                this.adjPrecincts = adjPrecincts;
	}
		
	private Precinct() {}

        @Id @GeneratedValue
	@Column(name = "ID")
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Column(name = "NAME")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
        @Column(name = "BOUNDARY")
	public String getBoundaryJSON() {
		return boundaryJSON;
	}
        
	public void setboundaryJSON(String boundaryJSON) throws Exception {
		if(!Validator.isJSONValid(boundaryJSON))
			throw new Exception("boundaryJSON value is not a valid JSON");
		this.boundaryJSON = boundaryJSON;
	}

        @Column(name = "DISTRICT")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String districtId) {
		this.district = districtId;
	}

        @Column(name = "ADJPRECINCTS")
        public String getAdjPrecincts() {
            return adjPrecincts;
        }

        public void setAdjPrecincts(String adjPrecincts) {
            this.adjPrecincts = adjPrecincts;
        }

        @Column(name = "STATE")
        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
	
}


