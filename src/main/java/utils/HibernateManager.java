package utils;

import electionResults.HouseResult;
import enums.ComparisonType;
import enums.ElectionType;
import enums.QueryField;
import enums.ShortName;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import politics.ConstitutionRequirements;
import politics.ConstitutionText;
import regions.District;
import regions.Precinct;
import regions.State;

/**
 *
 * @author Hengqi
 */
public class HibernateManager {

  private static HibernateManager instance;
  private final SessionFactory sessionFactory;
  private final Configuration configuration;
  private final Map<ShortName, State> states;

  /**
   * Constructor
   *
   * @throws java.lang.Exception
   * @throws HibernateException
   */
  private HibernateManager() throws Exception {
    configuration = new Configuration();
    configuration.configure();
    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
            configuration.getProperties()).buildServiceRegistry();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    states = new EnumMap<>(ShortName.class);
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
    session.saveOrUpdate(o);
    session.getTransaction().commit();
    session.close();
    return true;
  }

  /**
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
        criteria.add(qc.getType().getRestriction(qc.getFieldToCompare(), qc.getValue()));
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
    return getObjectsByConditions(c, Arrays.asList(queryCondition));
  }

  public Map<ShortName, State> getStates() {
    return this.states;
  }

  public State getStateByShortName(ShortName shortName) {
    if (this.getStates().containsKey(shortName)) {
      return this.getStates().get(shortName);
    }
    State state = new State();
    try {
      QueryCondition queryCondition = new QueryCondition(QueryField.shortName, shortName.toString(), ComparisonType.EQUAL);
      state = ((State) ((List) this.getObjectsByConditions(State.class, queryCondition)).get(0));
      queryCondition = new QueryCondition(QueryField.stateName, shortName.toString(), ComparisonType.EQUAL);
      state.setDistricts((Collection) this.getObjectsByConditions(District.class, queryCondition));
      queryCondition = new QueryCondition(QueryField.stateName, shortName.toString(), ComparisonType.EQUAL);
      state.setPrecincts((Collection) this.getObjectsByConditions(Precinct.class, queryCondition));
      state.initiatePrecinctsInDistrict();
      state.generateRegionObjects();
      queryCondition = new QueryCondition(QueryField.shortName, shortName.toString(), ComparisonType.EQUAL);
      state.setConstitutionRequirements((ConstitutionRequirements) ((List) this.getObjectsByConditions(ConstitutionRequirements.class, queryCondition)).get(0));
      queryCondition = new QueryCondition(QueryField.shortName, shortName.toString(), ComparisonType.EQUAL);
      state.getConstitutionTexts().addAll((Collection) this.getObjectsByConditions(ConstitutionText.class, queryCondition));
      queryCondition = new QueryCondition(QueryField.shortName, ShortName.USA.toString(), ComparisonType.EQUAL);
      state.getConstitutionTexts().addAll((Collection) this.getObjectsByConditions(ConstitutionText.class, queryCondition));
      queryCondition = new QueryCondition(QueryField.shortName, state.getShortName(), ComparisonType.EQUAL);
      Collection<HouseResult> statewideHouseResults = ((List) this.getObjectsByConditions(HouseResult.class, queryCondition));
      state.getPrecincts().forEach(precinct -> {
        statewideHouseResults.stream().filter((result) -> (result.getPrecinctName().equals(precinct.getName()))).forEachOrdered((result) -> {
          precinct.getElectionResults().get(ElectionType.HOUSE).add(result);
        });
      });
    } catch (Throwable e) {
      System.out.println(e.getMessage());
    }
    this.getStates().put(shortName, state);
    return state;
  }

}
