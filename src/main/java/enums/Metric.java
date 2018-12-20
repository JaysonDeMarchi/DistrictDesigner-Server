package enums;

import java.util.Comparator;
import java.util.HashMap;
import regions.District;
import regions.State;

/**
 *
 * @author Jayson
 */
public enum Metric {
  compactness {
    @Override
    public Double getValue(District district, Float weight) {
      double area = district.getGeometryShape().getArea();
      double perimeter = district.getGeometryShape().getLength();
      return weight*(4*Math.PI*area/(perimeter*perimeter));
    }
    
    @Override
    public Double getValue(State state, Float weight) {
      if(state.getDistricts().size()==0){
        return 0.0;
      }
      double stateCompactness = 0;
      for(District d:state.getDistricts()){
        stateCompactness+=getValue(d,weight);
      }
      return stateCompactness/state.getDistricts().size();
    }
  },
  partisan_Gerrymandering {
    @Override
    public Double getValue(District district,Float weight){
      Party losingParty = getLosingParty(district.getPartyResult());
      Party winningParty = losingParty == Party.DEMOCRATIC ? Party.REPUBLICAN : Party.DEMOCRATIC;

      int wastedVoteLosing = (district.getPartyResult().get(losingParty.toString())==null) ? 0 : district.getPartyResult().get(losingParty.toString());
      int wastedVoteWinning = (district.getPartyResult().get(winningParty.toString())==null) ? 0 : district.getPartyResult().get(winningParty.toString())-wastedVoteLosing;
      int totalVotes = ((district.getPartyResult().get(losingParty.toString())==null) ? 0 :  district.getPartyResult().get(losingParty.toString()))+
              ((district.getPartyResult().get(winningParty.toString())==null) ? 0 : district.getPartyResult().get(winningParty.toString()));
      
      return totalVotes == 0 ? 0 : weight*(1-((Math.abs(wastedVoteWinning-wastedVoteLosing)/(totalVotes+0.0))*2));
    }
    
    @Override
    public Double getValue(State state,Float weight){
      HashMap<String,Integer> statePartyResult = new HashMap<>();
      int democraticVotes = 0;
      int republicanVotes = 0;
      for(District d : state.getDistricts()){
        democraticVotes += (d.getPartyResult().get(Party.DEMOCRATIC.toString())==null) ? 0: d.getPartyResult().get(Party.DEMOCRATIC.toString());
        statePartyResult.put(Party.DEMOCRATIC.toString(), democraticVotes);
        republicanVotes += (d.getPartyResult().get(Party.REPUBLICAN.toString())==null) ? 0: d.getPartyResult().get(Party.REPUBLICAN.toString());
        statePartyResult.put(Party.REPUBLICAN.toString(), republicanVotes);
      }
      Party losingParty = democraticVotes>= republicanVotes ? Party.REPUBLICAN : Party.DEMOCRATIC;
      Party winningParty = losingParty == Party.DEMOCRATIC ? Party.REPUBLICAN : Party.DEMOCRATIC;

      int wastedVoteLosing = statePartyResult.get(losingParty.toString());
      int wastedVoteWinning = statePartyResult.get(winningParty.toString())-wastedVoteLosing;
      int totalVotes = democraticVotes+republicanVotes;
      return totalVotes == 0 ? 0 : weight*(1-((Math.abs(wastedVoteWinning-wastedVoteLosing)/(totalVotes+0.0))*2));
    }
  },
  population_Equality {
    @Override
    public Double getValue(State state,Float weight){
        District max = state.getDistricts().stream().max(Comparator.comparing(District::getPopulation)).get();
        District min = state.getDistricts().stream().min(Comparator.comparing(District::getPopulation)).get();
        return weight*(1-((max.getPopulation()-min.getPopulation())/(state.getPopulation()/Math.pow(state.getDistricts().size(),2)+0.0)));
    }
  },
  WASTED_VOTERS;

  public Double getValue(District district, Float weight) {
    return -1.0;
  }

  public Double getValue(State state, Float weight) {
    return -1.0;
  }
  
  private static Party getLosingParty(HashMap<String,Integer> partyResult ) {
  int demVotes = partyResult.get(Party.DEMOCRATIC.toString()) == null ? 0 : partyResult.get(Party.DEMOCRATIC.toString());
  int repVotes = partyResult.get(Party.REPUBLICAN.toString()) == null ? 0 : partyResult.get(Party.REPUBLICAN.toString());
  return demVotes>= repVotes ? Party.REPUBLICAN : Party.DEMOCRATIC;
  }

}
