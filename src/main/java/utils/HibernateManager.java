package utils;

import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    if (instance == null)
    {
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
  public boolean persistToDB(Object o) throws Throwable {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.persist(o);
    session.getTransaction().commit();
    session.close();
    return true;
  }

  /**
   * Method to get all the records for the given class from DB
   *
   * @param c: Class reference of class object to be queried
   * @return List containing all the records
   * @throws Throwable
   */
  public List<Object> getAllRecords(Class c) throws Throwable {
    return executeCriteria(c, null);
  }

  /**
   * Method to get records based on criteria passed as param
   *
   * @param c : Class reference of class object to be queried
   * @param criteria: map containing attribute and their values
   * @return List containing all the records
   * @throws Throwable
   */
  public List<Object> getRecordsBasedOnCriteria(Class c, Map<String, Object> criteria) throws Throwable {
    return executeCriteria(c, criteria);
  }

  private List<Object> executeCriteria(Class c, Map<String, Object> restrictions) throws Throwable {
    Session session = sessionFactory.openSession();
    org.hibernate.Transaction tr = session.beginTransaction();
    Criteria criteria = session.createCriteria(c);
    if (restrictions != null && !restrictions.isEmpty())
    {
      for (Map.Entry<String, Object> e : restrictions.entrySet())
      {
        criteria.add(Restrictions.eq(e.getKey(), e.getValue()));
      }
    }
    List records = criteria.list();
    tr.commit();
    session.close();
    return records;
  }

}
