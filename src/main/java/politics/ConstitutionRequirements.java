package politics;

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
@Table(name = "STATE")
public class ConstitutionRequirements {
  private String shortName;
  private double equalPopulation;
  private String compact;
  private String countyLine;
  private String contiguous;
  private String preserveCommunitiesIncumbants;
  
  public ConstitutionRequirements() {}
  
  public ConstitutionRequirements(String shortName) {
    this.shortName = shortName;
  }

  @Id
  @GeneratedValue
  @Column(name = "SHORTNAME")
  public String getShortName() {
    return this.shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @Column(name = "EQUALPOPULATION")
  public double getEqualPopulation() {
    return this.equalPopulation;
  }

  public void setEqualPopulation(double equalPopulation) {
    this.equalPopulation = equalPopulation;
  }

  @Column(name = "COMPACT")
  public String getCompact() {
    return this.compact;
  }

  public void setCompact(String compact) {
    this.compact = compact;
  }

  @Column(name = "COUNTYLINE")
  public String getCountyLine() {
    return this.countyLine;
  }

  public void setCountyLine(String countyLine) {
    this.countyLine = countyLine;
  }

  @Column(name = "CONTIGUOUS")
  public String getContiguous() {
    return this.contiguous;
  }

  public void setContiguous(String contiguous) {
    this.contiguous = contiguous;
  }

  @Column(name = "PRESERVE_COMMUNITIES_INCUMBANTS")
  public String getPreserveCommunitiesIncumbants() {
    return this.preserveCommunitiesIncumbants;
  }

  public void setPreserveCommunitiesIncumbants(String preserveCommunitiesIncumbants) {
    this.preserveCommunitiesIncumbants = preserveCommunitiesIncumbants;
  }
}
