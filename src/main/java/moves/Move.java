package moves;

import enums.MoveAttribute;
import regions.District;
import regions.Precinct;

/**
 *
 * @author Jayson
 */
public class Move {

  private Precinct precinct;
  private District oldDistrict;
  private District newDistrict;
  private Boolean successStatus;
  private Integer size;

  public Move(Precinct precinct, District oldDistrict, District newDistrict) {
    this.precinct = precinct;
    this.oldDistrict = oldDistrict;
    this.newDistrict = newDistrict;
    this.successStatus = false;
    this.size = (this.successStatus) ? MoveAttribute.SIZE_ON_SUCCESS.getValue() : MoveAttribute.SIZE_ON_FAILURE.getValue();
  }

  public Move(Precinct precinct, District oldDistrict, District newDistrict, Boolean successStatus) {
    this.precinct = precinct;
    this.oldDistrict = oldDistrict;
    this.newDistrict = newDistrict;
    this.successStatus = successStatus;
    this.size = (this.successStatus) ? MoveAttribute.SIZE_ON_SUCCESS.getValue() : MoveAttribute.SIZE_ON_FAILURE.getValue();
  }

  public Integer getSize() {
    return this.size;
  }

  public String getPrecinctId() {
    return this.precinct.getId();
  }

  public String getOldDistrictId() {
    return this.oldDistrict.getId();
  }

  public String getNewDistrictId() {
    return this.newDistrict.getId();
  }

  public Boolean getSuccessStatus() {
    return this.successStatus;
  }

  public void setSuccessStatus(Boolean status) {
    this.successStatus = status;
  }

}
