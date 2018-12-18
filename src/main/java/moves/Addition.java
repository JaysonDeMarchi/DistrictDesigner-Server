package moves;

import enums.MoveAttribute;
import regions.District;
import regions.Precinct;

/**
 *
 * @author Jayson
 */
public class Addition {

  private Precinct precinct;
  private District district;
  private Boolean successStatus;
  private Integer size;

  public Addition(Precinct precinct, District district, Boolean successStatus) {
    this.precinct = precinct;
    this.district = district;
    this.successStatus = successStatus;
    this.size = (this.successStatus) ? MoveAttribute.SIZE_ON_SUCCESS.getValue() : MoveAttribute.SIZE_ON_FAILURE.getValue();
  }

  public Integer getSize() {
    return this.size;
  }

  public String getPrecinctName() {
    return this.precinct.getName();
  }

  public String getDistrictName() {
    return this.district.getId();
  }

  public Boolean getSuccessStatus() {
    return this.successStatus;
  }

}
