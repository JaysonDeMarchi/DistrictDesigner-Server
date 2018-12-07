package utils;
import enums.ComparisonType;
/**
 *
 * @author Hengqi Zhu
 */
public class QueryCondition {
  String fieldToCompare;
  Object value;
  ComparisonType comparisonType;

  public QueryCondition(String fieldToCompare, Object value, ComparisonType Type) {
    this.fieldToCompare = fieldToCompare;
    this.value = value;
    this.comparisonType = Type;
  }

  public String getFieldToCompare() {
    return fieldToCompare;
  }

  public void setFieldToCompare(String fieldToCompare) {
    this.fieldToCompare = fieldToCompare;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public ComparisonType getType() {
    return comparisonType;
  }

  public void setType(ComparisonType Type) {
    this.comparisonType = Type;
  }

}
