package regions;

import enums.ShortName;
import java.util.Collection;

/**
 *
 * @author Jayson
 */
public class State {

  private ShortName shortName;
  private Collection<Precinct> precincts;

  public State(ShortName shortName) {
    this.shortName = shortName;
  }

  public Collection<Precinct> getPrecincts() {
    return this.precincts;
  }
}
