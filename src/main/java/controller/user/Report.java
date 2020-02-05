/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user;

import dal.ControlDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Validation;

/**
 *
 * @author root
 */
public class Report extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Report</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Report at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        int id = -1;
        int error = 0;
        try {
            id = Integer.parseInt(request.getParameter("id").trim());
        } catch (NumberFormatException ex) {
            response.sendError(1001, "invalid tweet id");
        }
        
        ControlDAO db = new ControlDAO();
        String username = (String) request.getSession().getAttribute("user");
        if (db.report(id, username)) {
            db.sendReport(id, username, 0);
            model.Post p = db.getTweet(id, username);
            response.getWriter().print("<button onclick=\"report("+p.getId()+")\" style=\"color:gray\" >Report</button>");
        } else {
            request.setAttribute("id", id); 
            request.getRequestDispatcher("../View/report.jsp").forward(request, response);
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
        int id = -1, type = -1;
        int error = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            type = Integer.parseInt(request.getParameter("type"));

        } catch (NumberFormatException ex) {
            response.sendError(1002, "Invalid tweet id or report type id");
        }
        Validation valid = new Validation();

        if (!valid.isTweetId(id)) {
            response.sendError(1003, "invalid tweet id");
        }
        if (!valid.isTypeId(type)) {
            response.sendError(1004, "invalid error type id");
        }

        String username = (String) request.getSession().getAttribute("user");
        ControlDAO db = new ControlDAO();
        db.sendReport(id, username, type);
        
        response.sendRedirect(request.getHeader("Referer") == null || request.getHeader("Referer").isEmpty() ? "dashboard" : request.getHeader("Referer"));
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
