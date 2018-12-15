package enums;

import politics.ConstitutionText;

/**
 *
 * @author Jayson
 */
public enum ConstitutionTextAttribute {
  JURISDICTION("jurisdiction") {
    @Override
    public String getValue(ConstitutionText text) {
      return text.getJurisdiction();
    }
  },
  SHORT_NAME("shortName") {
    @Override
    public String getValue(ConstitutionText text) {
      return text.getShortName();
    }
  },
  DOCUMENT("document") {
    @Override
    public String getValue(ConstitutionText text) {
      return text.getDocument();
    }
  },
  OFFICE("office") {
    @Override
    public String getValue(ConstitutionText text) {
      return text.getOffice();
    }
  },
  ARTICLE("article") {
    @Override
    public String getValue(ConstitutionText text) {
      return text.getArticle();
    }
  },
  SECTION("section") {
    @Override
    public String getValue(ConstitutionText text) {
      return text.getSection();
    }
  },
  BODY("body") {
    @Override
    public String getValue(ConstitutionText text) {
      return text.getBody();
    }
  },
  NOTES("notes") {
    @Override
    public String getValue(ConstitutionText text) {
      return text.getNotes();
    }
  };

  private final String name;

  private ConstitutionTextAttribute(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public String getValue(ConstitutionText text) {
    return "NA";
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
