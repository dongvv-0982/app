/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user;

import dal.ControlDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author root
 */
public class Follow extends HttpServlet {

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
        String username = request.getParameter("username");
        String follower = request.getParameter("follower");
        int action = Integer.parseInt(request.getParameter("action"));

        String ref = request.getHeader("Referer");
        ControlDAO db = new ControlDAO();
        request.getSession().setAttribute("csrf", "");
        //action = 0 => add follow
        switch (action) {
            //action == 1 => unfollow
            case 0:
                db.addFollow(username, follower);
                response.getWriter().print("<button class=\"btn btn-outline-primary\""
                        + " onclick=\"follow('"+username+"','"+follower+"',1\">Waiting</button>");
                break;
            //action == 2 => accept follow
            case 1:
                
                db.unFollow(username, follower);
                response.getWriter().print("<button class=\"btn btn-outline-primary\""
                        + " onclick=\"follow('"+username+"','"+follower+"',0\">Follow</button>");
                break;
            case 2:
                db.acceptFollow(username, follower, 1);

                String suser = (String) request.getSession().getAttribute("user");
                ArrayList<User> followers = db.getFollowers(suser);
                for (User f : followers) {
                    response.getWriter().print("<div class=\"d-flex flex-row mb-2 justify-content-between\">\n"
                            + "                                        <div class=\"text-left col-6\">" + f.getName() + " @" + f.getUsername() + "</div>\n"
                            + "                                        <div class=\"col-6 d-flex flex-row justify-content-end\">\n"
                            + "                                            <button class=\"btn-sm mr-2 btn-primary\" onclick=\"follow('" + suser + "','" + f.getUsername() + "', 2)\">Approve</button>\n"
                            + "                                            <button class=\"btn-sm btn-danger\" onclick=\"follow('" + suser + "','" + f.getUsername() + "', 3)\">Reject</button>\n"
                            + "                                        </div>\n"
                            + "                                    </div>");
                }
                break;
            default:
                db.acceptFollow(username, follower, 2);
                break;
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
