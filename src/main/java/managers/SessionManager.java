package managers;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Jayson
 */
@WebListener
public class SessionManager implements HttpSessionListener {

  @Override
  public void sessionCreated(final HttpSessionEvent se) {
    System.out.println("SESSION CREATED");
    final HttpSession session = se.getSession();
    final ServletContext context = session.getServletContext();
    context.setAttribute(session.getId(), session);
  }

  @Override
  public void sessionDestroyed(final HttpSessionEvent se) {
    final HttpSession session = se.getSession();
    final ServletContext context = session.getServletContext();
    context.removeAttribute(session.getId());
  }
}
