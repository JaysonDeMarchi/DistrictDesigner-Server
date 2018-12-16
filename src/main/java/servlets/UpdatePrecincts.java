package servlets;

import algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.ResponseAttribute;
import enums.SessionAttribute;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import managers.UpdateManager;
import moves.Move;

/**
 *
 * @author Jayson
 */
@WebServlet(name = "UpdatePrecincts", urlPatterns = {"/UpdatePrecincts"})
public class UpdatePrecincts extends HttpServlet {

  ObjectMapper mapper = new ObjectMapper();

  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();

    processResponse(response, request, getUpdates((Algorithm) session.getAttribute(SessionAttribute.ALGORITHM.toString())));
  }

  private Collection<Move> getUpdates(Algorithm algorithm) {
    UpdateManager updateManager = algorithm.getUpdateManager();
    algorithm.run();
    Collection<Move> moves = updateManager.getMoves();
    updateManager.reset();
    return moves;
  }

  private void processResponse(HttpServletResponse response, HttpServletRequest request, Collection<Move> moves) throws IOException {
    ObjectNode responseBody = mapper.createObjectNode();
    PrintWriter pw = response.getWriter();
    response.setContentType("application/json;charset=UTF-8");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    ArrayNode movesArray = responseBody.putArray(ResponseAttribute.UPDATED_PRECINCTS.toString());
    moves.stream().map((move) -> {
      ObjectNode updatedPrecinct = mapper.createObjectNode();
      updatedPrecinct.put(ResponseAttribute.PRECINCT_ID.toString(), move.getPrecinctId())
              .put(ResponseAttribute.OLD_DISTRICT_ID.toString(), move.getOldDistrictId())
              .put(ResponseAttribute.NEW_DISTRICT_ID.toString(), move.getNewDistrictId())
              .put(ResponseAttribute.SUCCESS_STATUS.toString(), move.getSuccessStatus());
      return updatedPrecinct;
    }).forEach((updatedPrecinct) -> {
      movesArray.add(updatedPrecinct);
    });

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
