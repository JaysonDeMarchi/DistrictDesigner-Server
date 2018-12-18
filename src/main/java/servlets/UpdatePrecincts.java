package servlets;

import algorithms.Algorithm;
import beans.UpdateRequestParams;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.wololo.jts2geojson.GeoJSONWriter;
import enums.ResponseAttribute;
import enums.SessionAttribute;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import managers.UpdateManager;
import regions.District;

/**
 *
 * @author Jayson
 */
@WebServlet(name = "UpdatePrecincts", urlPatterns = {"/UpdatePrecincts"})
public class UpdatePrecincts extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BufferedReader br = request.getReader();
    String requestBody = br.readLine();
    UpdateRequestParams requestParams = mapper.readValue(requestBody, UpdateRequestParams.class);

    HttpSession session = getSessionById(requestParams.getSessionId());
    processResponse(response, request, getUpdates((Algorithm) session.getAttribute(SessionAttribute.ALGORITHM.toString())));
  }

  private JsonNode getUpdates(Algorithm algorithm) {
    System.out.println("Run");
    UpdateManager updateManager = algorithm.run();
    ObjectNode updateNode = mapper.createObjectNode();
    ArrayNode districtNodes = mapper.createArrayNode();
    GeoJSONWriter writer = new GeoJSONWriter();
    algorithm.getState().getDistricts().stream().map((district) -> {
      ObjectNode districtNode = mapper.createObjectNode();
      districtNode.put("geometry", writer.write(district.getGeometryShape()).toString());
      districtNode.put("properties", getPropertiesNode(district));
      return districtNode;
    }).forEachOrdered(districtNode -> {
      districtNodes.add(districtNode);
    });

    updateManager.reset();
    return updateNode.put(ResponseAttribute.DISTRICTS.toString(), districtNodes);
  }

  public ObjectNode getPropertiesNode(District district) {
    ObjectNode propertiesNode = mapper.createObjectNode();
    propertiesNode.put("population", district.getPopulation());
    return propertiesNode;
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, JsonNode updateNode) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    PrintWriter pw = response.getWriter();
    response.setContentType("application/json;charset=UTF-8");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//    ArrayNode movesArray = responseBody.putArray(ResponseAttribute.UPDATED_PRECINCTS.toString());
//    moves.stream().map((move) -> {
//      ObjectNode updatedPrecinct = mapper.createObjectNode();
//      updatedPrecinct.put(ResponseAttribute.PRECINCT_ID.toString(), move.getPrecinctId())
//              .put(ResponseAttribute.OLD_DISTRICT_ID.toString(), move.getOldDistrictId())
//              .put(ResponseAttribute.NEW_DISTRICT_ID.toString(), move.getNewDistrictId())
//              .put(ResponseAttribute.SUCCESS_STATUS.toString(), move.getSuccessStatus());
//      return updatedPrecinct;
//    }).forEach((updatedPrecinct) -> {
//      movesArray.add(updatedPrecinct);
//    });
    responseBody.put(ResponseAttribute.DISTRICTS.toString(), updateNode);
    pw.print(responseBody.toString());
  }

  private HttpSession getSessionById(final String sessionId) {
    final ServletContext context = getServletContext();
    final HttpSession session = (HttpSession) context.getAttribute(sessionId);
    return session;
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
