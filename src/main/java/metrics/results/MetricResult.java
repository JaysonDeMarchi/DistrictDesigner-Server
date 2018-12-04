package metrics.results;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jayson
 */
public class MetricResult {

  protected Map<String, Object> values;

  public MetricResult() {
    values = new LinkedHashMap<>();
  }

  public MetricResult(MetricException e) {
    this();
    setError(e.getMessage());
    setScore("null");
  }

  protected void addField(String key, Object value) {
    values.put(key, value);
  }

  protected Object getValue(String key) {
    return values.get(key);
  }

  protected void removeField(String value) {
    values.remove(value);
  }

  public void setScore(Object score) {
    addField(SCORE, score);
  }

  public Object getScore() {
    return getValue(SCORE);
  }

  public void addProperty(String key, Object value) {
    if (getValue(PROPERTIES) == null) {
      values.put(PROPERTIES, new LinkedHashMap<String, Object>());
    }
    ((Map<String, Object>) values.get(PROPERTIES)).put(key, value);
  }

  public Object getProperty(String property) {
    if (getValue(PROPERTIES) == null) {
      return null;
    } else {
      Map<String, Object> props = (Map<String, Object>) values.get(PROPERTIES);
      return props.get(property);
    }
  }

  /* This private getter is so we can serialize the contents of the Map, without its name to reduce the nesting in
    the JSON.
   */
  @JsonValue
  private Map<String, Object> getMapToSerialize() {
    return values;
  }

  public void setError(String errorMsg) {
    addField(ERR_NO_DATA, errorMsg);
  }

  public List<String> getHeaders() {
    List<String> headers = new ArrayList<>();
    Map<String, Object> props = (Map<String, Object>) values.get(PROPERTIES);
    if (props == null) {
      return headers;
    }
    headers.addAll(props.keySet());
    return headers;
  }

  public List<Object> getRow() {
    List<Object> row = new ArrayList<>();
    Map<String, Object> props = (Map<String, Object>) values.get(PROPERTIES);
    if (props == null) {
      return row;
    }
    row.addAll(props.values());
    return row;
  }

}
