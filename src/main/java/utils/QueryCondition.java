package utils;
import enums.ComparisonType;
import enums.QueryField;
/**
 *
 * @author Hengqi Zhu
 */
public class QueryCondition {
  private String fieldToCompare;
  private Object value;
  private ComparisonType comparisonType;

  public QueryCondition(QueryField fieldToCompare, Object value, ComparisonType Type) {
    this.fieldToCompare = fieldToCompare.toString();
    this.value = value;
    this.comparisonType = Type;
  }

  public String getFieldToCompare() {
    return this.fieldToCompare;
  }

  public void setFieldToCompare(String fieldToCompare) {
    this.fieldToCompare = fieldToCompare;
  }

  public Object getValue() {
    return this.value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public ComparisonType getType() {
    return this.comparisonType;
  }

  public void setType(ComparisonType Type) {
    this.comparisonType = Type;
  }

}
