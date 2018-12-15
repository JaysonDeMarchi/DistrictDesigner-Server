package beans;

/**
 *
 * @author Jayson
 */
public class CreateAccountParams {

  private String username;
  private String password;

  public CreateAccountParams() {
    super();
  }

  public CreateAccountParams(String username, String password) {
    this.username = username;
    this.password = password;
  }

}
