/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user;

import dal.ControlDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import model.Post;
import org.apache.commons.validator.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author root
 */
public class Dashboard extends HttpServlet {

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
        ControlDAO db = new ControlDAO();
        ArrayList<Post> tweets;
        String getAll = request.getParameter("getall");
        String suser = (String) request.getSession().getAttribute("user");
        response.addHeader("X-Frame-Options", "deny");
        if (getAll != null && getAll.equals("1")) {
            tweets = db.getAllTweet(suser);
        } else {
            tweets = db.get10Tweet(0, suser);
        }
        for (Post tweet : tweets) {
            String content = tweet.getContent();
            List<String> url = extractUrls(content);
            String curl = null;
            System.out.println("url : " + (url == null ? "Null url" : url));
            UrlValidator validator = new UrlValidator();
            for (String string : url) {
                if (validator.isValid(string)) {
                    curl = string;
                    break;
                }
            }
            String text = null;
            String imgUrl = null;
            if (curl != null) {

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
                 text = getHtmlText(doc);
                 imgUrl = getImageFromUrl(curl,doc);
            }
            tweet.setImgUrl(imgUrl);
            tweet.setLink(curl);
            tweet.setDescription(text);
        }
        request.setAttribute("tweets", tweets);
        request.getRequestDispatcher("../View/dashboard.jsp").forward(request, response);
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

    public List<String> extractUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
//        String urlRegex = "_^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?$_iuS";
//        String urlRegex = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";
        String urlRegex = "(?:(?:https?|ftp|file):\\/\\/|www\\.|ftp\\.)(?:\\([-A-Z0-9+&@#\\/%=~_|$?!:,.]*\\)|[-A-Z0-9+&@#\\/%=~_|$?!:,.])*(?:\\([-A-Z0-9+&@#\\/%=~_|$?!:,.]*\\)|[A-Z0-9+&@#\\/%=~_|$])";
//        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);
        System.out.println("mathcher: " + urlMatcher);
        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

    private String getHtmlText(Document doc) {

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
        if(desContent == null){
            
        }
        return desContent;
    }

    private String getImageFromUrl( String url, Document doc) {
        String imgUrl = "";
        try {
            imgUrl = doc.select("img").get(0).attr("src");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            imgUrl = null;
        }
        if(imgUrl != null && imgUrl.startsWith("/")){
            imgUrl = url.split("/")[0]+url.split("/")[1]+"//" + url.split("/")[2]+ imgUrl;
        }
        return imgUrl;
    }

    
}
