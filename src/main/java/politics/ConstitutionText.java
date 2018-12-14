package politics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author hqzhu
 */
@Entity
@Table(name = "CONSTITUTION_TEXT")
public class ConstitutionText {
  private String jurisdiction;
  private String shortName;
  private String document;
  private String office;
  private String article;
  private String section;
  private String body;
  private String notes;
  
  public ConstitutionText(){}
  
 
  @Column(name = "JURISDICTION")
  public String getJurisdiction() {
    return jurisdiction;
  }


  public void setJurisdiction(String jurisdiction) {
    this.jurisdiction = jurisdiction;
  }
  
  @Id
  @Column(name = "SHORTNAME")
  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @Column(name="DOCUMENT")
  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  @Id
  @Column(name = "OFFICE")
  public String getOffice() {
    return office;
  }

  public void setOffice(String office) {
    this.office = office;
  }

  @Id
  @Column(name="ARTICLE")
  public String getArticle() {
    return article;
  }

  public void setArticle(String article) {
    this.article = article;
  }

  @Column(name = "SECTION")
  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  @Column(name = "BODY")
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Column(name = "NOTES")
  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
  
  
  
}
