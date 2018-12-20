package regions;

import electionResults.Election;
import electionResults.HouseResult;
import enums.ElectionType;
import enums.Metric;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.wololo.jts2geojson.GeoJSONReader;

/**
 * @author Hengqi
 */
@Entity
@Table(name = "DISTRICT")
public class District extends Region implements Serializable, Cloneable {

  private String id;
  private String boundary;
  private String stateName;
  private State state;
  private Collection<Precinct> precincts;
  private Collection<Geometry> geoBoundary;
  private Collection<Precinct> candidatePrecincts;
  private HashMap<String, Integer> partyResult;
  private GeometryFactory geometryFactory;
  private GeoJSONReader reader;
  private Integer population;
  private Double objectiveFunction;

  public District() {
    this.candidatePrecincts = new ArrayList<>();
    this.geometryFactory = new GeometryFactory();
    this.reader = new GeoJSONReader();
    this.precincts = new HashSet<>();
    this.geoBoundary = new HashSet<>();
    this.partyResult = new HashMap<>();
    this.population = 0;
  }

  public District(String id, Precinct seed) {
    this.candidatePrecincts = new ArrayList<>();

    this.geometryFactory = new GeometryFactory();
    this.reader = new GeoJSONReader();
    this.id = id;
    this.precincts = new HashSet<>();
    this.partyResult = new HashMap<>();
    this.geoBoundary = new HashSet<>();
    this.candidatePrecincts = new LinkedHashSet<>();
    this.population = new Integer(0);
    this.addPrecinct(seed);
    seed.setDistrictId(id);
  }

  @Id
  @GeneratedValue
  @Column(name = "ID")
  @Override
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "STATE")
  public String getStateName() {
    return this.stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  @Transient
  public State getState() {
    return this.state;
  }

  public void setState(State state) {
    this.state = state;
  }

  @Column(name = "BOUNDARY")
  public String getBoundary() {
    return this.boundary;
  }

  public void setBoundary(String boundary) {
    this.boundary = boundary;
  }

  @Transient
  public Integer getPopulation() {
    return this.population;
  }

  public void setPopulation(Integer population) {
    this.population = population;
  }

  @Transient
  public Collection<Precinct> getCandidatePrecincts() {
    return candidatePrecincts;
  }

  public void setCandidatePrecincts(Collection<Precinct> candidatePrecincts) {
    this.candidatePrecincts = candidatePrecincts;
    this.candidatePrecincts.removeAll(this.precincts);
    for (Precinct p : this.candidatePrecincts) {
      if (p.getDistrictId() == null) {
        if (!p.getDistrictId().equals("")) {
          this.candidatePrecincts.remove(p);
        }
      }
    }
  }

  @Transient
  public Collection<Precinct> getPrecincts() {
    return this.precincts;
  }

  public void setPrecincts(Collection<Precinct> precincts) {
    this.precincts = precincts;
  }

  public void addPrecinct(Precinct precinct) {
    this.precincts.add(precinct);
    this.geoBoundary.add(this.reader.read(precinct.getBoundary()));
    this.population += precinct.getPopulation();
    Collection<Election> houseResults = precinct.getElectionResults().get(ElectionType.HOUSE);
    for (Election hr : houseResults) {
      HouseResult temp = (HouseResult) hr;
      int value;
      if (this.partyResult.get(temp.getParty()) == null) {
        value = temp.getNumOfVoter();
      } else {
        value = this.partyResult.get(temp.getParty()) + temp.getNumOfVoter();
      }
      this.partyResult.put(temp.getParty(), value);
    }
    precinct.setDistrictId(this.id);
  }

  public void removePrecinct(Precinct precinct) {
    this.precincts.remove(precinct);
    this.geoBoundary.remove(this.reader.read(precinct.getBoundary()));
    this.population -= precinct.getPopulation();
    Collection<Election> houseResults = precinct.getElectionResults().get(ElectionType.HOUSE);
    for (Election hr : houseResults) {
      HouseResult temp = (HouseResult) hr;
      int value = this.partyResult.get(temp.getParty()) - temp.getNumOfVoter();
      this.partyResult.put(temp.getParty(), value);
    }
    precinct.setDistrictId("");
  }

  @Transient
  public Collection<Geometry> getGeoBoundary() {
    return this.geoBoundary;
  }

  public void setGeoBoundary(Collection<Geometry> geoBoundary) {
    this.geoBoundary = geoBoundary;
  }

  @Transient
  public GeometryFactory getGeometryFactory() {
    return this.geometryFactory;
  }

  public void setGeometryFactory(GeometryFactory geometryFactory) {
    this.geometryFactory = geometryFactory;
  }

  @Transient
  public Geometry getGeometryShape() {
    if (this.getGeometryFactory().buildGeometry(this.getGeoBoundary()) instanceof Polygon) {
      return this.getGeometryFactory().buildGeometry(this.getGeoBoundary());
    }
    GeometryCollection geometryShape = (GeometryCollection) this.getGeometryFactory().buildGeometry(this.getGeoBoundary());
    return geometryShape.union();
  }

  @Transient
  public GeoJSONReader getReader() {
    return reader;
  }

  public void setReader(GeoJSONReader reader) {
    this.reader = reader;
  }

  @Transient
  public HashMap<String, Integer> getPartyResult() {
    return this.partyResult;
  }

  @Transient
  public Double getObjectiveFunction() {
    return this.objectiveFunction;
  }

  public void setObjectiveFunction(Double objectiveFunction) {
    this.objectiveFunction = objectiveFunction;
  }

  public Double calculateObjectiveFunction(EnumMap<Metric, Float> weights) {
    Double objectiveFunc = 0.0;
    Integer validMetrics = 0;
    for (Metric metric : Metric.values()) {
      Double result = metric.getValue(this, weights.get(metric));
      if (result >= 0.0) {
        objectiveFunc += metric.getValue(this, weights.get(metric));
        validMetrics++;
      }
    }
    return objectiveFunc / validMetrics;
  }

  @Transient
  public Set<Precinct> getEdgePrecincts() {
    return this.getPrecincts().stream()
            .filter((precinct) -> precinct.isEdgePrecinct())
            .collect(Collectors.toSet());
  }

  public void setEdgePrecincts() {
  }

  @Override
  public District clone() throws CloneNotSupportedException {
    District newDistrict = new District();
    newDistrict.setBoundary(this.getBoundary());
    Collection<Precinct> candidates = new ArrayList<>();
    this.getCandidatePrecincts().forEach((p) -> {
      candidates.add(p);
    });
    newDistrict.setCandidatePrecincts(candidates);
    newDistrict.setGeoBoundary(this.getGeoBoundary());
    newDistrict.setId(this.getId());
    newDistrict.setObjectiveFunction(this.getObjectiveFunction());
    newDistrict.setPopulation(this.getPopulation());
    Collection<Precinct> currentPrecints = new ArrayList<>();
    this.getPrecincts().forEach((p) -> {
      currentPrecints.add(p);
    });
    newDistrict.setPrecincts(currentPrecints);
    newDistrict.setStateName(this.getStateName());
    return newDistrict;
  }

}
