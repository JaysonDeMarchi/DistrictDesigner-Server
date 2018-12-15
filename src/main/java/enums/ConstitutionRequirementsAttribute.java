package enums;

import politics.ConstitutionRequirements;

/**
 *
 * @author Jayson
 */
public enum ConstitutionRequirementsAttribute {
  SHORT_NAME("shortName") {
    @Override
    public String getValue(ConstitutionRequirements requirements) {
      return requirements.getShortName();
    }
  },
  EQUAL_POPULATION("equalPopulation") {
    @Override
    public String getValue(ConstitutionRequirements requirements) {
      return Double.toString(requirements.getEqualPopulation());
    }
  },
  COMPACT("compact") {
    @Override
    public String getValue(ConstitutionRequirements requirements) {
      return requirements.getCompact();
    }
  },
  COUNTY_LINE("countyLine") {
    @Override
    public String getValue(ConstitutionRequirements requirements) {
      return requirements.getCountyLine();
    }
  },
  CONTIGUOUS("contiguous") {
    @Override
    public String getValue(ConstitutionRequirements requirements) {
      return requirements.getContiguous();
    }
  },
  PRESERVE_COMMUNITIES_INCUMBANTS("preserveCommunitiesIncumbants") {
    @Override
    public String getValue(ConstitutionRequirements requirements) {
      return requirements.getPreserveCommunitiesIncumbants();
    }
  };

  private final String name;

  private ConstitutionRequirementsAttribute(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public String getValue(ConstitutionRequirements constitutionRequirements) {
    return "NA";
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
