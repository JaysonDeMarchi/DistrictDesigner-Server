package enums;

import electionResults.Election;

/**
 *
 * @author Jayson
 */
public enum ElectionAttribute {
  CANDIDATE {
    @Override
    public String getValue(Election election) {
      return election.getCandidate();
    }
  },
  PARTY {
    @Override
    public String getValue(Election election) {
      return election.getParty();
    }
  },
  VOTER_COUNT {
    @Override
    public String getValue(Election election) {
      return election.getVoterCount();
    }
  };

  public abstract String getValue(Election election);
}
