package be.test.tomboek.servlet;

import be.test.tomboek.message.Producer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageServlet extends HttpServlet {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MessageServlet.class);

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
        LOGGER.debug("MessageServlet");

        String message = request.getParameter("message");
        String txtCount = request.getParameter("txtCount");
        Long count = Long.valueOf(txtCount);
        
        try {
            Producer.Produce(message, count);
        } catch (JMSException ex) {
            LOGGER.error("MessageServlet error: {}", ex.toString());
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Go back</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Go back to index</h1>");
            out.println("<p><a href=\"index.jsp\">index</a></p>");
            out.println("</body>");
            out.println("</html>");
            out.close();
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
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
