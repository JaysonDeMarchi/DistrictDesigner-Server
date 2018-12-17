package electionResults;

/**
 *
 * @author Jayson
 */
public abstract class Election {

  public Election() {
  }

  public abstract String getCandidate();

  public abstract String getParty();

  public abstract String getVoterCount();

  public abstract String getOffice();
}
