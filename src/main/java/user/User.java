package user;

import enums.AccountType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Jayson
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

  private String password;
  private String type;
  private String username;

  public User() {
  }

  public User(String username, String password) {
    this.password = password;
    this.type = AccountType.GENERAL.toString();
    this.username = username;
  }

  @Column(name = "PASSWORD")
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Id
  @Column(name = "USERNAME")
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Column(name = "TYPE")
  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
