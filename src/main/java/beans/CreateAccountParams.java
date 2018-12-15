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

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
