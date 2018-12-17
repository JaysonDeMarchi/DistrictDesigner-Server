package enums;

/**
 *
 * @author Jayson
 */
public enum ResponseAttribute {
  ALGO_STARTED,
  ALGO_STOPPED,
  CONSTITUTION_REQUIREMENTS,
  CONSTITUTION_TEXT,
  ERROR_MESSAGE,
  NEW_DISTRICT_ID,
  OLD_DISTRICT_ID,
  PRECINCT_ID,
  SESSION_ID,
  SUCCESS_STATUS,
  UPDATED_PRECINCTS,
  USER_CREATED {
    @Override
    public String getErrorMessage() {
      return "Error: User could not be created";
    }
  },
  USER_KEY,
  USER_LOGGED_IN {
    @Override
    public String getErrorMessage() {
      return "Error: Invalid Username";
    }
  },
  USER_LOGGED_OUT {
    @Override
    public String getErrorMessage() {
      return "Error: User could not log out";
    }
  },
  WEIGHTS_SAVED{
    @Override
      public String getErrorMessage() {
        return "Error: Weights could not be saved";
      }
  }
  ;

  public String getErrorMessage() {
    return "Error";
  }

}
