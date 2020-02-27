/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.swing.text.html.HTML.Attribute.CODE;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author root
 */
public class GetDescription extends HttpServlet {

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
            out.println("<title>Servlet GetDescription</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetDescription at " + request.getContextPath() + "</h1>");
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
        String text = null;
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
        text = getHtmlText(curl, doc);
//        response.getWriter().print("<div class='card-body'> <a href='" + curl + "' style='color: inherit;'><p class='card-text'>" + (text == null ? curl : text) + "</p></a></div>");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<div class='card-body'> <a href='" + curl + "' style='color: inherit;'><p class='card-text'>" + (text == null ? curl : text) + "</p></a></div>");
        out.close();
    }

    private String getHtmlText(String url, Document doc) {

        String desContent;
        try {
            desContent = doc.select("meta[name=Description]").get(0).attr("content");
        } catch (IndexOutOfBoundsException ex) {
            try {
                desContent = doc.select("p").first().text();
            } catch (NullPointerException exx) {
                desContent = null;
            }
        }
        String x;
        if (desContent == null) {
            x = url;
        } else {
            x = new String(desContent.getBytes(), Charset.forName("UTF-8"));
        }

        return x;
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
