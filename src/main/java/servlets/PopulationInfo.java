package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.ComparisonType;
import enums.Demographic;
import enums.ElectionType;
import enums.QueryField;
import enums.ResponseAttribute;
import enums.ShortName;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import regions.District;
import regions.Precinct;
import regions.State;
import utils.HibernateManager;
import utils.QueryCondition;

/**
 *
 * @author Jayson
 */
@WebServlet(name = "PopulationInfo", urlPatterns = {"/PopulationInfo"})
public class PopulationInfo extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    ShortName shortName = ShortName.valueOf(request.getParameter(QueryField.shortName.toString()));
    processResponse(response, request, getStateByShortName(shortName.toString()));
  }

  private State getStateByShortName(String shortName) {
    State state = new State();
    try {
      HibernateManager hb = new HibernateManager();
      QueryCondition queryCondition = new QueryCondition(QueryField.shortName, shortName, ComparisonType.EQUAL);
      state = ((State) ((List) hb.getObjectsByConditions(State.class, queryCondition)).get(0));
      queryCondition = new QueryCondition(QueryField.stateName, shortName, ComparisonType.EQUAL);
      state.setDistricts((Collection) hb.getObjectsByConditions(District.class, queryCondition));
      queryCondition = new QueryCondition(QueryField.stateName, shortName, ComparisonType.EQUAL);
      state.setPrecincts((Collection) hb.getObjectsByConditions(Precinct.class, queryCondition));
      state.initiatePrecinctsInDistrict();
    } catch (Throwable e) {
      System.out.println(e.getMessage());
    }
    return state;
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, State state) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    ArrayNode populationInfo = mapper.createArrayNode();
    state.getPrecincts().stream().map((precinct) -> {
      ObjectNode precinctNode = mapper.createObjectNode();
      ObjectNode demographicsNode = mapper.createObjectNode();
      ObjectNode electionNode = mapper.createObjectNode();
      for (Demographic d : Demographic.values()) {
        demographicsNode.put(d.toString(), d.getPopulation(precinct));
      }
      precinctNode.put("Demographics", demographicsNode);
      precinctNode.put("ElectionResults", electionNode);
      return precinctNode;
    }).forEachOrdered((precinctNode) -> populationInfo.add(precinctNode));
    try (PrintWriter pw = response.getWriter()) {
      response.setContentType("application/json;charset=UTF-8");
      response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
      responseBody.put(ResponseAttribute.POPULATION_INFO.toString(), populationInfo);
      pw.print(responseBody);
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
