package enums;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author hqzhu
 */
public enum ComparisonType {
  EQUAL,
  GREATER_THAN{
    @Override
    public Criterion getRestriction(String field, Object value) {
      return Restrictions.gt(field, value);
    }
  },
  LESS_THAN{
    @Override
    public Criterion getRestriction(String field, Object value) {
      return Restrictions.lt(field, value);
    }
  },
  GREATER_THAN_OR_EQUAL{
    @Override
    public Criterion getRestriction(String field, Object value) {
      return Restrictions.ge(field, value);
    }
  },
  LESS_THAN_OR_EQUAL{
    @Override
    public Criterion getRestriction(String field, Object value) {
      return Restrictions.le(field, value);
    }
  };
  
  public Criterion getRestriction(String field, Object value) {
    return Restrictions.eq(field, value);
  }
}
