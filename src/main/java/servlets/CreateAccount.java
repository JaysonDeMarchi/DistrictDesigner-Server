package servlets;

import beans.CreateAccountParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.ComparisonType;
import enums.QueryField;
import enums.ResponseAttribute;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.User;
import utils.HibernateManager;
import utils.QueryCondition;

/**
 *
 * @author Jayson
 */
@WebServlet(name = "CreateAccount", urlPatterns = {"/CreateAccount"})
public class CreateAccount extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, Exception, Throwable {
    BufferedReader br = request.getReader();
    String requestBody = br.readLine();
    CreateAccountParams accountParams = mapper.readValue(requestBody, CreateAccountParams.class);
    processResponse(response, request, createUser(accountParams));
  }

  public Boolean createUser(CreateAccountParams accountParams) throws Exception, Throwable {
    String username = accountParams.getUsername();
    User user = new User(username, accountParams.getPassword());
    HibernateManager hb = HibernateManager.getInstance();
    QueryCondition queryCondition = new QueryCondition(QueryField.username, username, ComparisonType.EQUAL);
    ArrayList<User> existingUsers = (ArrayList) hb.getObjectsByConditions(User.class, queryCondition);
    if (existingUsers.isEmpty()) {
      return hb.saveObjectToDB(user);
    }
    return false;
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, Boolean userCreated) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    try (PrintWriter pw = response.getWriter()) {
      response.setContentType("application/json;charset=UTF-8");
      response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
      responseBody.put(ResponseAttribute.USER_CREATED.toString(), userCreated);
      if (!userCreated) {
        responseBody.put(ResponseAttribute.ERROR_MESSAGE.toString(), ResponseAttribute.USER_CREATED.getErrorMessage());
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
