/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hqzhu
 */
@Entity
@Table(name="precinct")
public class Precinct implements Serializable {

    
    @Column(name="countyId")
    @Id
    private String countyId;
    
    @Column(name="countyName")
    private String countyName;

    public Precinct() {
    }
    
    
    
    public Precinct(String id, String name){
        this.countyId=id;
        this.countyName=name;
    
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    /**
     * @return the countyName
     */
    public String getCountyName() {
        return countyName;
    }

    /**
     * @param countyName the countyName to set
     */
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
    
    
    
}
