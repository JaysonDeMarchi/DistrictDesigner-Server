package electionResults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hqzhu
 */
@Entity
@Table(name = "HOUSE_RESULT")
public class HouseResult {
  private Integer id;
  private String precinctName;
  private String candidate;
  private String praty;
  private String shortName;
  private Integer numOfVoter;
  
  public HouseResult(){}

  @Id
  @GeneratedValue
  @Column(name = "ID")
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name="PRECINCT")
  public String getPrecinctName() {
    return this.precinctName;
  }

  public void setPrecinctName(String precinctName) {
    this.precinctName = precinctName;
  }

  @Column(name="CANDIDATE")
  public String getCandidate() {
    return this.candidate;
  }

  public void setCandidate(String candidate) {
    this.candidate = candidate;
  }

  @Column(name = "PARTY")
  public String getPraty() {
    return this.praty;
  }

  public void setPraty(String praty) {
    this.praty = praty;
  }

  @Column(name="STATE")
  public String getShortName() {
    return this.shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @Column(name="NUMOFVOTER")
  public Integer getNumOfVoter() {
    return this.numOfVoter;
  }

  public void setNumOfVoter(Integer numOfVoter) {
    this.numOfVoter = numOfVoter;
  }
  
  
  
  
  
}
