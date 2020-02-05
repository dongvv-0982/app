/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user;

import dal.ControlDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import model.Validation;

/**
 *
 * @author root
 */
public class Post extends HttpServlet {

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
        
        String content = request.getParameter("content");
        Integer type_id;
        try{
            type_id = Integer.parseInt(request.getParameter("type_id"));
        } catch(NumberFormatException ex){
            type_id = 1;
        }
        ControlDAO db = new ControlDAO();
        String username = (String)request.getSession().getAttribute("user");
        User author = db.getUser(username);
        Validation v = new Validation();
        content = v.validate(content);
        //check title and content not null
        if(content != null){
           //check length of title and content
           if(content.length() <= 150){
               db.uploadTweet(author.getUsername(),  content, type_id);
               request.setAttribute("error", 0);
               response.sendRedirect("dashboard");
           } else{
               request.setAttribute("error", 2);
               response.sendRedirect("dashboard");
           }
               
        } else {
            request.setAttribute("error", 1);
            response.sendRedirect("dashboard");
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
