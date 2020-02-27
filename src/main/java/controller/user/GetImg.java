/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author root
 */
public class GetImg extends HttpServlet {

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
            out.println("<title>Servlet GetImg</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetImg at " + request.getContextPath() + "</h1>");
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
        String curl = request.getParameter("url");
        String imgUrl = null;
        String command
                = "curl -X GET " + curl;
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new File("/home/"));
        Process process = processBuilder.start();

        Scanner s = new Scanner(process.getInputStream()).useDelimiter("\\A");
        String result = "";

        while (s.hasNext()) {
            result += s.next();
        }
//                System.out.println("result : \n" + result);
        Document doc = Jsoup.parse(result);
        imgUrl = getImageFromUrl(curl, doc);
        System.out.println(imgUrl);
        response.getWriter().print("<img class='card-img-top mt-4' style='height: 200px; width: auto; max-height:100%; margin: auto; display: block; align: center' src='" + (imgUrl == null ? 1 : imgUrl) + "' alt='" + curl + "'>" );

    }

    private String getImageFromUrl(String url, Document doc) {
        String imgUrl = "";

        try {
            imgUrl = doc.select("img").get(0).attr("src");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            imgUrl = null;
        }
        if (imgUrl != null && imgUrl.startsWith("/")) {
            imgUrl = url.split("/")[0] + url.split("/")[1] + "//" + url.split("/")[2] + imgUrl;
        }
       
        return imgUrl;
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
        response.getWriter().print("Method not Allow");
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
