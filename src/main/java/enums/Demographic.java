package enums;

import regions.Precinct;

/**
 *
 * @author Jayson
 */
public enum Demographic {
  ASIAN {
    @Override
    public Integer getPopulation(Precinct precinct) {
      return precinct.getAsian();
    }
  },
  BLACK {
    @Override
    public Integer getPopulation(Precinct precinct) {
      return precinct.getBlack();
    }
  },
  HISPANIC {
    @Override
    public Integer getPopulation(Precinct precinct) {
      return precinct.getHispanic();
    }
  },
  WHITE {
    @Override
    public Integer getPopulation(Precinct precinct) {
      return precinct.getWhite();
    }
  };

  public abstract Integer getPopulation(Precinct precinct);
}
