package user;

/**
 *
 * @author Jayson
 */
public class User {

  private Boolean isAdmin;
  private String password;
  private String username;

  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.isAdmin = false;
  }

  public String getUsername() {
    return this.username;
  }

  public Boolean getIsAdmin() {
    return this.isAdmin;
  }

  public void setIsAdmin(Boolean status) {
    this.isAdmin = status;
  }
}
