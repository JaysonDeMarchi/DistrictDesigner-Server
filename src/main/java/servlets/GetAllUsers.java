package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.ComparisonType;
import enums.QueryField;
import user.User;
import user.UserWeights;
import utils.HibernateManager;
import utils.QueryCondition;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "GetAllUsers", urlPatterns = {"/GetAllUsers"})
public class GetAllUsers extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, Exception, Throwable {
    String username = request.getParameter("username");
    HibernateManager hb = HibernateManager.getInstance();
    QueryCondition queryCondition = null;
    ArrayList<User> users = (ArrayList) hb.getObjectsByConditions(User.class, queryCondition);
    processResponse(response, request, users);
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, ArrayList<User> users) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    try (PrintWriter pw = response.getWriter()) {
      response.setContentType("application/json;charset=UTF-8");
      response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
      pw.print(mapper.writer().writeValueAsString(users));
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (Throwable ex) {
      Logger.getLogger(GetAllUsers.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (Throwable ex) {
      Logger.getLogger(GetAllUsers.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
    }
  }
}
