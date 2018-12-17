package enums;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import regions.Region;
import regions.State;
import utils.HibernateManager;

/**
 *
 * @author Jayson
 */
public enum ShortName {
  AK,
  AL,
  AR,
  AZ,
  CA,
  CO,
  CT,
  DE,
  FL,
  GA,
  HI,
  IA,
  ID,
  IL,
  IN,
  KS,
  KY,
  LA,
  MA,
  MD,
  ME,
  MI,
  MN,
  MO,
  MS,
  MT,
  NC,
  ND,
  NE,
  NH,
  NJ,
  NM,
  NV,
  NY,
  OH,
  OK,
  OR,
  PA,
  RI,
  SC,
  SD,
  TN,
  TX,
  USA {
    @Override
    public ObjectNode getOriginalMap() {
      ObjectNode region = mapper.createObjectNode();
      return region;
    }
  },
  UT,
  VA,
  VT,
  WA,
  WI,
  WV,
  WY;

  ObjectMapper mapper = new ObjectMapper();
  private final String geomertyLabel = "geometry";
  private final String propertiesLabel = "properties";

  public ObjectNode getOriginalMap() throws Exception {
    ObjectNode region = mapper.createObjectNode();
    HibernateManager hb = HibernateManager.getInstance();
    State state = hb.getStateByShortName(this);
    ArrayNode districtNodes = mapper.createArrayNode();
    ArrayNode precinctNodes = mapper.createArrayNode();
    state.getDistricts().stream().map((district) -> {
      ObjectNode districtNode = mapper.createObjectNode();
      districtNode.put(geomertyLabel, district.getBoundary());
      districtNode.put(propertiesLabel, getPropertiesNode(district));
      return districtNode;
    }).forEachOrdered(districtNode -> {
      districtNodes.add(districtNode);
    });
    state.getPrecincts().stream().map((precinct) -> {
      ObjectNode precinctNode = mapper.createObjectNode();
      precinctNode.put(geomertyLabel, precinct.getBoundary());
      ObjectNode propertiesNode = getPropertiesNode(precinct);
      propertiesNode.put(ResponseAttribute.POPULATION.toString(), precinct.getPopulation());
      for (Demographic demographic : Demographic.values()) {
        propertiesNode.put(demographic.toString(), demographic.getPopulation(precinct));
      }
      ObjectNode electionNode = mapper.createObjectNode();
      for (ElectionType electionType : ElectionType.values()) {
        ArrayNode electionTypeNode = mapper.createArrayNode();
        if (!precinct.getElectionResults().isEmpty()) {
          precinct.getElectionResults().get(electionType).stream().map((result) -> {
            ObjectNode resultNode = mapper.createObjectNode();
            for (ElectionAttribute attribute : ElectionAttribute.values()) {
              resultNode.put(attribute.toString(), attribute.getValue(result));
            }
            return resultNode;
          }).forEachOrdered((resultNode) -> {
            electionTypeNode.add(resultNode);
          });
        }
        electionNode.put(electionType.toString(), electionTypeNode);
      }
      precinctNode.put(ResponseAttribute.ELECTION_RESULTS.toString(), electionNode);

      precinctNode.put(propertiesLabel, propertiesNode);
      return precinctNode;
    }).forEachOrdered(precinctNode -> {
      precinctNodes.add(precinctNode);
    });
    region.put(ResponseAttribute.DISTRICTS.toString(), districtNodes);
    region.put(ResponseAttribute.PRECINCTS.toString(), precinctNodes);
    return region;
  }

  public ObjectNode getPropertiesNode(Region region) {
    ObjectNode propertiesNode = mapper.createObjectNode();
    for (PropertyAttribute property : PropertyAttribute.values()) {
      propertiesNode.put(property.toString(), property.getValue(region));
    }
    return propertiesNode;
  }
}
