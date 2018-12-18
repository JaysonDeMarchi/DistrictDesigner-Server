package servlets;

import beans.CreateAccountParams;
import beans.SaveWeightsParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.ComparisonType;
import enums.QueryField;
import enums.ResponseAttribute;
import user.User;
import user.UserWeights;
import utils.HibernateManager;
import utils.QueryCondition;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoadWeights", urlPatterns = {"/LoadWeights"})
public class LoadWeights extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, Exception, Throwable {
    String username = request.getParameter("username");
    HibernateManager hb = HibernateManager.getInstance();
    QueryCondition queryCondition = new QueryCondition(QueryField.username, username, ComparisonType.EQUAL);
    ArrayList<UserWeights> userWeights = (ArrayList) hb.getObjectsByConditions(UserWeights.class, queryCondition);
    processResponse(response, request, userWeights);
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, ArrayList<UserWeights> uw) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    try (PrintWriter pw = response.getWriter()) {
      response.setContentType("application/json;charset=UTF-8");
      response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
      pw.print(mapper.writer().writeValueAsString(uw));
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (Throwable ex) {
      Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (Throwable ex) {
      Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
