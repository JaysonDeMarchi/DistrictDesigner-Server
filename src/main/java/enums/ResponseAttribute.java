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
  USER_CREATED {
    @Override
    public String getErrorMessage() {
      return "Error: User could not be created";
    }
  },
  USER_LOGGED_IN,
  USER_LOGGED_OUT;

  public String getErrorMessage() {
    return "Error";
  }
}
