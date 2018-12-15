package utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author Hengqi
 */
public class HibernateManager {

  private static HibernateManager instance;
  private SessionFactory sessionFactory;
  private Configuration configuration;

  /**
   * Constructor
   *
   * @throws HibernateException
   */
  public HibernateManager() throws Exception {
    configuration = new Configuration();
    configuration.configure();
    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
            configuration.getProperties()).buildServiceRegistry();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
  }

  /**
   *
   * @return instance of HibernateManager
   * @throws HibernateException
   */
  public static synchronized HibernateManager getInstance() throws Exception {
    if (instance == null) {
      instance = new HibernateManager();
    }
    return instance;
  }

  /**
   * Method to save the object attributes as a new entry in DB
   *
   * @param Object of the class to be saved into DB
   * @return success statue
   * @throws Throwable : use getStackTrace() to find the error
   */
  public boolean saveObjectToDB(Object o) throws Throwable {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.persist(o);
    session.getTransaction().commit();
    session.close();
    return true;
  }

  /**
   * Method to get objects as entries in DB by conditions if conditions are
   * empty return all objects
   *
   * @param c objects to be get from DB
   * @param conditions constraints to data look for
   * @return a collection of objects
   * @throws Throwable : use getStackTrace() to find the error
   */
  public Collection<Object> getObjectsByConditions(Class c, List<QueryCondition> conditions) throws Throwable {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    Criteria criteria = session.createCriteria(c);
    if (conditions != null && !conditions.isEmpty()) {
      for (QueryCondition qc : conditions) {
        switch (qc.getType()) {
          case EQUAL:
            criteria.add(Restrictions.eq(qc.getFieldToCompare(), qc.getValue()));
            break;
          case LESS_THAN:
            criteria.add(Restrictions.lt(qc.getFieldToCompare(), qc.getValue()));
            break;
          case GREATER_THAN:
            criteria.add(Restrictions.gt(qc.getFieldToCompare(), qc.getValue()));
            break;
          case GREATER_THAN_OR_EQUAL:
            criteria.add(Restrictions.ge(qc.getFieldToCompare(), qc.getValue()));
            break;
          case LESS_THAN_OR_EQUAL:
            criteria.add(Restrictions.le(qc.getFieldToCompare(), qc.getValue()));
            break;
          default:
            break;
        }
      }
    }
    Collection<Object> entites = criteria.list();
    transaction.commit();
    session.close();
    return entites;
  }

  /**
   * Overload of getObjectsByConditions
   */
  public Collection<Object> getObjectsByConditions(Class c, QueryCondition queryCondition) throws Throwable {
      return getObjectsByConditions(c,Arrays.asList(queryCondition));

  }

}
