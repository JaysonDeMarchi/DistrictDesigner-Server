package servlets;

import algorithms.RegionGrowing;
import algorithms.SimulatedAnnealing;
import beans.StartRequestParams;
import enums.AlgorithmType;
import enums.Metric;
import enums.ResponseAttribute;
import enums.SessionAttribute;
import enums.ShortName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static enums.ShortName.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    
    
    Map<Metric, Float> criteria = new HashMap<>();
    RegionGrowing rg = new RegionGrowing(UT, criteria);
    
    String requestBody = br.readLine();
    StartRequestParams requestParams = mapper.readValue(requestBody, StartRequestParams.class);
    HttpSession session = request.getSession();

    processResponse(response, request, initiateAlgorithm(session, requestParams));
  }

  private Boolean initiateAlgorithm(HttpSession session, StartRequestParams requestParams) {
    AlgorithmType algoType = requestParams.getAlgoType();
    ShortName shortName = requestParams.getShortName();
    Map<Metric, Float> weights = requestParams.getWeights();

    if (algoType == AlgorithmType.REGION_GROWING) {
      session.setAttribute(SessionAttribute.ALGORITHM.toString(), new RegionGrowing(shortName, weights));
    } else if (algoType == AlgorithmType.SIMULATED_ANNEALING) {
      session.setAttribute(SessionAttribute.ALGORITHM.toString(), new SimulatedAnnealing(shortName, weights));
    }
    return true;
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, Boolean status) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    PrintWriter pw = response.getWriter();

    response.setContentType("application/json;charset=UTF-8");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    responseBody.put(ResponseAttribute.ALGO_STARTED.toString(), status);
    pw.print(responseBody.toString());
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
