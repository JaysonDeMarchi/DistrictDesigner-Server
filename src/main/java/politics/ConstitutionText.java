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
@Table(name = "CONSTITUTION_TEXT")
public class ConstitutionText {
  private Integer id;
  private String jurisdiction;
  private String shortName;
  private String document;
  private String office;
  private String article;
  private String section;
  private String body;
  private String notes;
  
  public ConstitutionText(){}
  
 
  @Id
  @GeneratedValue
  @Column(name = "ROWID")
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "JURISDICTION")
  public String getJurisdiction() {
    return this.jurisdiction;
  }

  public void setJurisdiction(String jurisdiction) {
    this.jurisdiction = jurisdiction;
  }
  

  @Column(name = "SHORTNAME")
  public String getShortName() {
    return this.shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @Column(name="DOCUMENT")
  public String getDocument() {
    return this.document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  @Column(name = "OFFICE")
  public String getOffice() {
    return this.office;
  }

  public void setOffice(String office) {
    this.office = office;
  }

  @Column(name="ARTICLE")
  public String getArticle() {
    return this.article;
  }

  public void setArticle(String article) {
    this.article = article;
  }

  @Column(name = "SECTION")
  public String getSection() {
    return this.section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  @Column(name = "BODY")
  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Column(name = "NOTES")
  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
  
}
