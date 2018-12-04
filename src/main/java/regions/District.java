package regions;

import java.util.Collection;

/**
 *
 * @author Jayson
 */
public class District {

  Collection<Precinct> precincts;

  public District() {
    this.precincts = null;
  }

  public Boolean add(Precinct precinct) {
    this.getPrecincts().add(precinct);
    return true;
  }

  private Collection<Precinct> getPrecincts() {
    return this.precincts;
  }
}
