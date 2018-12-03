package moves;

import com.fasterxml.jackson.databind.JsonNode;
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

  public Integer getPrecinctId() {
    return this.precinct.getId();
  }

  public Integer getOldDistrictId() {
    return this.oldDistrict.getId();
  }

  public Integer getNewDistrictId() {
    return this.newDistrict.getId();
  }

  public Boolean getSuccessStatus() {
    return this.successStatus;
  }

}
