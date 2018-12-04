/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    List<Object> precincts;

    public District(String id, String boundaryJSON, String state) {
        this.id = id;
        this.boundaryJSON = boundaryJSON;
        this.state = state;
    }

    public District(){}

    @Id @GeneratedValue
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "BOUNDARY")
    public String getBoundaryJSON() {
        return boundaryJSON;
    }

    public void setBoundaryJSON(String boundaryJSON) {
        this.boundaryJSON = boundaryJSON;
    }

    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Transient
    public List<Object> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(List<Object> precincts) {
        this.precincts = precincts;
    }
     
}
