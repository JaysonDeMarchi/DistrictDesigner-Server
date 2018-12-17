package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.ComparisonType;
import enums.ConstitutionRequirementsAttribute;
import enums.ConstitutionTextAttribute;
import enums.QueryField;
import enums.ResponseAttribute;
import enums.ShortName;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import politics.ConstitutionRequirements;
import politics.ConstitutionText;
import regions.State;
import utils.HibernateManager;
import utils.QueryCondition;

/**
 *
 * @author Jayson
 */
@WebServlet(name = "Constitution", urlPatterns = {"/Constitution"})
public class Constitution extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, Exception {
    ShortName shortName = ShortName.valueOf(request.getParameter(QueryField.shortName.toString()));
    HibernateManager hb = HibernateManager.getInstance();
    State state = hb.getStateByShortName(shortName);
    processResponse(response, request, state.getConstitutionRequirements(), state.getConstitutionTexts());
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, ConstitutionRequirements constitutionRequirements, Collection<ConstitutionText> constitutionTexts) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    ObjectNode constitutionRequirementsNode = mapper.createObjectNode();
    for (ConstitutionRequirementsAttribute requirement : ConstitutionRequirementsAttribute.values()) {
      constitutionRequirementsNode.put(requirement.toString(), requirement.getValue(constitutionRequirements));
    }
    ArrayNode constitutionTextNodes = mapper.createArrayNode();
    constitutionTexts.stream().map((text) -> {
      ObjectNode textNode = mapper.createObjectNode();
      for (ConstitutionTextAttribute textAttribute : ConstitutionTextAttribute.values()) {
        textNode.put(textAttribute.toString(), textAttribute.getValue(text));
      }
      return textNode;
    }).forEachOrdered((textNode) -> {
      constitutionTextNodes.add(textNode);
    });
    try (PrintWriter pw = response.getWriter()) {
      response.setContentType("application/json;charset=UTF-8");
      response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
      responseBody.put(ResponseAttribute.CONSTITUTION_REQUIREMENTS.toString(), constitutionRequirementsNode);
      responseBody.put(ResponseAttribute.CONSTITUTION_TEXT.toString(), constitutionTextNodes);
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
      Logger.getLogger(Constitution.class
              .getName()).log(Level.SEVERE, null, ex);
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
      Logger.getLogger(Constitution.class
              .getName()).log(Level.SEVERE, null, ex);
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
