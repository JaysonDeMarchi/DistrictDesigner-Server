package servlets;

import algorithms.Algorithm;
import beans.StartRequestParams;
import com.fasterxml.jackson.databind.JsonNode;
import enums.AlgorithmType;
import enums.Metric;
import enums.ResponseAttribute;
import enums.SessionAttribute;
import enums.ShortName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.SelectionType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.wololo.jts2geojson.GeoJSONWriter;
import regions.District;

/**
 *
 * @author Jayson
 */
@WebServlet(name = "StartAlgorithm", urlPatterns = {"/StartAlgorithm"})
public class StartAlgorithm extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    BufferedReader br = request.getReader();
    String requestBody = br.readLine();
    StartRequestParams requestParams = mapper.readValue(requestBody, StartRequestParams.class);
    HttpSession session = request.getSession();
    processResponse(response, request, session, initiateAlgorithm(session, requestParams));
  }

  private Boolean initiateAlgorithm(HttpSession session, StartRequestParams requestParams) {
    AlgorithmType algoType = requestParams.getAlgoType();
    ShortName shortName = requestParams.getShortName();
    Integer numOfDistricts = requestParams.getNumOfDistricts();
    SelectionType selectionType = requestParams.getSelectionType();
    EnumMap<Metric, Float> weights = requestParams.getWeights();
    session.setAttribute(SessionAttribute.ALGORITHM.toString(), algoType.createAlgorithm(shortName, selectionType, weights, numOfDistricts));
    return true;
  }

  private JsonNode getSeededDistricts(Algorithm algorithm) {
    ArrayNode districtNodes = mapper.createArrayNode();
    GeoJSONWriter writer = new GeoJSONWriter();
    for (District d : algorithm.getState().getDistricts()) {
      ObjectNode districtNode = mapper.createObjectNode();
      districtNode.put("geomerty", writer.write(d.getGeometryShape()).toString());
      ObjectNode properties = mapper.createObjectNode();
      districtNode.put("properties", properties);
      districtNodes.add(districtNode);
    }
//    algorithm.getState().getDistricts().stream().map((district) -> {
//      System.out.println("Add district");
//      ObjectNode districtNode = mapper.createObjectNode();
//      districtNode.put("geometry", writer.write(district.getGeometryShape()).toString());
//      System.out.println("DistrictNode");
//      return districtNode;
//    }).forEachOrdered(districtNode -> {
//      districtNodes.add(districtNode);
//    });
    System.out.println("RETURNS");
    return districtNodes;
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, HttpSession session, Boolean status) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    try (PrintWriter pw = response.getWriter()) {
      response.setContentType("application/json;charset=UTF-8");
      response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
      responseBody.put(ResponseAttribute.ALGO_STARTED.toString(), status);
      responseBody.put(ResponseAttribute.DISTRICTS.toString(), getSeededDistricts(((Algorithm) session.getAttribute(SessionAttribute.ALGORITHM.toString()))));
      responseBody.put(ResponseAttribute.SESSION_ID.toString(), session.getId());
      pw.print(responseBody.toString());
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
