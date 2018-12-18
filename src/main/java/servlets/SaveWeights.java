package servlets;

import beans.SaveWeightsParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.ResponseAttribute;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.UserWeights;
import utils.HibernateManager;

/**
 *
 * @author Jayson
 */
@WebServlet(name = "SaveWeights", urlPatterns = {"/SaveWeights"})
public class SaveWeights extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, Exception, Throwable {
    BufferedReader br = request.getReader();
    String requestBody = br.readLine();
    SaveWeightsParams saveWeightsParams = mapper.readValue(requestBody, SaveWeightsParams.class);
    processResponse(response, request, saveWeights(saveWeightsParams));
  }

  public Boolean saveWeights(SaveWeightsParams saveWeightsParams) throws Exception, Throwable {
    String username = saveWeightsParams.getUsername();
    Float compactness = saveWeightsParams.getCompactness();
    Float populationEquality = saveWeightsParams.getPopulationEquality();
    Float partisanGerrymandering = saveWeightsParams.getPartisanGerrymandering();
    UserWeights uw = new UserWeights(username,compactness,populationEquality,partisanGerrymandering);
    HibernateManager hb = new HibernateManager();
    return hb.saveObjectToDB(uw);
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, Boolean weightsSaved) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    try (PrintWriter pw = response.getWriter()) {
      response.setContentType("application/json;charset=UTF-8");
      response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
      responseBody.put(ResponseAttribute.WEIGHTS_SAVED.toString(), weightsSaved);
      if (!weightsSaved) {
        responseBody.put(ResponseAttribute.ERROR_MESSAGE.toString(), ResponseAttribute.WEIGHTS_SAVED.getErrorMessage());
      }
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
    try {
      processRequest(request, response);
    } catch (Throwable ex) {
      Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
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
    } catch (Throwable ex) {
      Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
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
