/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feature;
import model.Post;
import model.Role;
import model.User;

/**
 *
 * @author root
 */
public abstract class DAO {
    public Connection DAO() {
        String url = "jdbc:mysql://localhost:3306/vuln";
        String user = "vudong";
        String pass = "pass";
        try {
            Class.forName("com.mysql.jdbc.Driver");
           return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public abstract boolean addUser(String username, String password, String email, String name);
    public abstract String login(String username, String password);
    public abstract void uploadTweet(String username,  String content, int type, String link);
    public abstract Post getTweet(int id,String suser);
    public abstract ArrayList<Post> get10Tweet(int index, String suser);
    public abstract void likeTweet(int postid, String username);
    public abstract void comment(int postid, String username, String content);
    public abstract boolean  sendReport(int id, String username, int type);
    public abstract Role getRole(String username);
    public abstract User getUser(String username);
    public abstract void follow(String username, String followedUser);
    public abstract ArrayList<String> viewFollow(String username);
    public abstract ArrayList<Feature> getFeature(int roleid);
    public abstract ArrayList<model.Report> getAllReport();
}
