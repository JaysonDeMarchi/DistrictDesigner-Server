package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import electionResults.Election;
import enums.Demographic;
import enums.ElectionAttribute;
import enums.ElectionType;
import enums.RequestParam;
import enums.ResponseAttribute;
import enums.ShortName;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import regions.State;
import utils.HibernateManager;

/**
 *
 * @author Jayson
 */
@WebServlet(name = "PopulationInfo", urlPatterns = {"/PopulationInfo"})
public class PopulationInfo extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, Exception {
    ShortName shortName = ShortName.valueOf(request.getParameter(RequestParam.SHORT_NAME.getName()));
    HibernateManager hb = HibernateManager.getInstance();
    processResponse(response, request, hb.getStateByShortName(shortName));
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
      precinctNode.put(ResponseAttribute.DEMOGRAPHICS.toString(), demographicsNode);
      precinctNode.put(ResponseAttribute.ELECTION_RESULTS.toString(), electionNode);
      return precinctNode;
    }).forEachOrdered((precinctNode) -> populationInfo.add(precinctNode));
    try (PrintWriter pw = response.getWriter()) {
      System.out.println("READY TO SEND");
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
    try {
      processRequest(request, response);
    } catch (Exception ex) {
      Logger.getLogger(PopulationInfo.class.getName()).log(Level.SEVERE, null, ex);
    }
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
    try {
      processRequest(request, response);
    } catch (Exception ex) {
      Logger.getLogger(PopulationInfo.class.getName()).log(Level.SEVERE, null, ex);
    }
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
