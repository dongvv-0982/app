/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
public class ControlDAO extends DAO {

    Connection connection;

    public ControlDAO() {
        connection = super.DAO();
        generateReportType();
    }
    public static HashMap<Integer, String> reportTypes = new HashMap<>();

    private void generateReportType() {
        reportTypes.put(0, "Broken Link");
        reportTypes.put(1, "Inappropriate Content");
        reportTypes.put(2, "Spam");
    }

    @Override
    public boolean addUser(String username, String password, String email, String name) {
        try {
            String url = "INSERT INTO user( username, password, email, roleid, name) "
                    + "VALUES(?,?,?,1,?);";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, name);
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String login(String username, String password) {
        try {
            String url = "SELECT username FROM user WHERE username = ? and password = ?;";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return username;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void uploadTweet(String username, String content, int type, String link) {
        try {
            String url = "INSERT INTO post(content, author, type, link) "
                    + "VALUES(?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(url);
            
            statement.setString(1, content);
            statement.setString(2, username);
            statement.setInt(3, type);
            statement.setString(4, link);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Post getTweet(int id, String suser) {
        try {
            String url = "SELECT null, content, author, type, id, reportid "
                    + "FROM post WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Post u = new Post();
                u.setId(rs.getInt(5));
                u.setTitle(rs.getString(1));
                u.setContent(rs.getString(2));
                u.setTypename(rs.getInt(4) == 0 ? "private" : "public");
                u.setAuthor(getUser(rs.getString(3)));
                u.setLikes(getLikes(rs.getInt(5)));
                u.setReports(getReports(rs.getInt(5)));
                u.setReport(rs.getString(6));
                if (u.getTypename().equals("private")) {
                    String username = u.getAuthor().getUsername();
                    if (isFollowed(username, suser) || username.equals(suser)) {
                        return u;
                    } else {
                        return null;
                    }
                } else {
                    return u;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Post> get10Tweet(int index, String suser) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String url = "SELECT null, content, author, type, id, reportid, link "
                    + "FROM post ORDER BY time_create DESC ";
            PreparedStatement statement = connection.prepareStatement(url);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Post u = new Post();
                u.setId(rs.getInt(5));
                u.setTitle(rs.getString(1));
                u.setContent(rs.getString(2));
                u.setTypename(rs.getInt(4) == 0 ? "private" : "public");
                u.setAuthor(getUser(rs.getString(3)));
                u.setLikes(getLikes(rs.getInt(5)));
                u.setReports(getReports(rs.getInt(5)));
                u.setReport(rs.getString(6));
                u.setLink(rs.getString(7));
                if (u.getTypename().equals("private")) {
                    String username = u.getAuthor().getUsername();
                    if (isFollowed(username, suser) || username.equals(suser)) {
                        posts.add(u);
                    }
                } else {
                    posts.add(u);
                }
                if (posts.size() == 10) {
                    break;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    private boolean like(int id, String username) {

        try {
            String url = "SELECT * FROM postlike WHERE postid = ? and username = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            statement.setString(2, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean report(int id, String username) {

        try {
            String url = "SELECT * FROM report WHERE postid = ? and reporter = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            statement.setString(2, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public void likeTweet(int id, String username) {
        try {
            String url = "";
            if (like(id, username)) {
                url = "DELETE FROM postlike WHERE postid = ? and username = ? ";
            } else {
                url = "INSERT INTO postlike( postid, username) "
                        + "VALUES(?,?);";
            }
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void comment(int postid, String username, String content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isLegal(String user, String suser, Post u) {
        if (u.getTypename().equals("private")) {
            String username = u.getAuthor().getUsername();
            if (isFollowed(username, suser) || username.equals(suser)) {
                return true;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean sendReport(int id, String username, int type) {
        try {
            Post p = getTweet(id, username);
            if(p == null)
                return false;
            String url = "";
            if (report(id, username)) {
                url = "DELETE FROM report WHERE postid = ? and reporter = ? ";
            } else {
                url = "INSERT INTO report( postid, reporter, typeid) "
                        + "VALUES(?,?,?);";
            }
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            statement.setString(2, username);
            if (!report(id, username)) {
                statement.setInt(3, type);
            }
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Role getRole(String username) {
        try {
            String url = "SELECT r.name, r.id FROM user u INNER JOIN role r "
                    + "ON u.roleid = r.id WHERE u.username = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt(2));
                r.setName(rs.getString(1));
                r.setFeatures(getFeature(r.getId()));
                return r;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void follow(String username, String followedUser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> viewFollow(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Feature> getFeature(int roleid) {
        ArrayList<Feature> features = new ArrayList<>();
        try {
            String url = "SELECT f.path, f.name FROM role_feature u INNER JOIN role r "
                    + "ON u.roleid = r.id INNER JOIN feature f "
                    + "ON f.path = u.path WHERE r.id = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, roleid);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Feature f = new Feature();
                f.setName(rs.getString(2));
                f.setPath(rs.getString(1));
                features.add(f);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return features;
    }

    @Override
    public User getUser(String username) {
        try {
            String url = "SELECT username, password, email, name, avatar FROM user  WHERE username = ?"
                    + " ";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUsername(username);
                u.setEmail(rs.getString(3));
                u.setRole(getRole(username));
                u.setName(rs.getString(4));
                u.setImageBase64(rs.getString(5));
                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Post> getAllTweet(String suser) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String url = "SELECT null, content, author, type, id, reportid, link "
                    + "FROM post ORDER BY time_create DESC";
            PreparedStatement statement = connection.prepareStatement(url);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Post u = new Post();
                u.setId(rs.getInt(5));
                u.setTitle(rs.getString(1));
                u.setContent(rs.getString(2));
                u.setTypename(rs.getInt(4) == 0 ? "private" : "public");
                u.setAuthor(getUser(rs.getString(3)));
                u.setLikes(getLikes(rs.getInt(5)));
                u.setReports(getReports(rs.getInt(5)));
                u.setReport(rs.getString(6));
                u.setLink(rs.getString(7));
                if (u.getTypename().equals("private")) {
                    String username = u.getAuthor().getUsername();
                    if (isFollowed(username, suser) || username.equals(suser)) {
                        posts.add(u);
                    }
                } else {
                    posts.add(u);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    private ArrayList<String> getLikes(int tweet_id) {
        ArrayList<String> usernames = new ArrayList<>();

        try {
            String url = "SELECT username "
                    + "FROM postlike WHERE postid = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, tweet_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                usernames.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usernames;
    }

    private ArrayList<String> getReports(int tweet_id) {
        ArrayList<String> usernames = new ArrayList<>();

        try {
            String url = "SELECT reporter "
                    + "FROM report WHERE postid = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, tweet_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                usernames.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usernames;
    }

    public boolean checkid(int id) {
        try {
            String url = "SELECT * FROM post WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public ArrayList<model.Report> getAllReport() {
        ArrayList<model.Report> reports = new ArrayList<>();

        try {
            String url = "SELECT r.id, r.reporter,  t.author, t.content, r.typeid "
                    + "FROM report r INNER JOIN post t ON  r.postid = t.id";
            PreparedStatement statement = connection.prepareStatement(url);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                model.Report r = new model.Report();
                r.setId(rs.getInt(1));
                r.setAuthor(rs.getString(3));
                r.setContent(rs.getString(4));
                r.setReporter(rs.getString(2));
                r.setType(reportTypes.get(Integer.parseInt(rs.getString(5))));
                reports.add(r);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reports;
    }

    public void deleteReport(int id) {

        try {
            String url = "DELETE FROM report WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deletePost(int id, String report) {
        deleteComment(id);
        deleteLike(id);
        try {
            String url = "UPDATE  post SET content=null, link=null, reportid=? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(2, id);
            statement.setString(1, report);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTweetId(int id) {

        try {
            String url = "SELECT postid FROM report WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    private void deleteComment(int id) {
        try {
            String url = "DELETE FROM tweet_comment WHERE tweet_id = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteLike(int id) {
        try {
            String url = "DELETE FROM postlike WHERE posid = ?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Post> getAllOwnedPost(String username, String suser) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String url = "SELECT null, content, author, type, id,reportid, link "
                    + "FROM post "
                    + " WHERE author=?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Post u = new Post();
                u.setId(rs.getInt(5));
                u.setTitle(rs.getString(1));
                u.setContent(rs.getString(2));
                u.setTypename(rs.getInt(4) == 0 ? "private" : "public");
                u.setAuthor(getUser(rs.getString(3)));
                u.setLikes(getLikes(rs.getInt(5)));
                u.setReports(getReports(rs.getInt(5)));
                u.setReport(rs.getString(6));
                u.setLink(rs.getString(7));
                if (u.getTypename().equals("private")) {
                    if (isFollowed(username, suser) || username.equals(suser)) {
                        posts.add(u);
                    }
                } else {
                    posts.add(u);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    public ArrayList<User> getFollowers(String username) {
        ArrayList<User> followers = new ArrayList<>();
        try {
            String url = "SELECT null, f.username, f.follower, f.status, u.name, u.email,u.avatar FROM  follow f "
                    + "INNER JOIN user u ON f.follower=u.username "
                    + " WHERE f.username=? and f.status=0";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setName(rs.getString(5));
                u.setEmail(rs.getString(6));
                u.setUsername(rs.getString(3));
                u.setImageBase64(rs.getString(7));
                followers.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return followers;
    }

    public boolean isFollow(String username, String follower) {

        try {
            String url = "SELECT * FROM follow WHERE username=? and follower = ? and  status = 0";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setString(1, username);
            statement.setString(2, follower);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isFollowed(String username, String follower) {

        try {
            String url = "SELECT * FROM follow WHERE username=? and follower = ? and  status=1";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setString(1, username);
            statement.setString(2, follower);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void addFollow(String username, String follower) {
        try {
            if (!isFollow(username, follower)) {
                String url = "INSERT INTO follow( username, follower, status) "
                        + "VALUES(?,?, 0);";
                PreparedStatement statement = connection.prepareStatement(url);
                statement.setString(1, username);
                statement.setString(2, follower);

                statement.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void acceptFollow(String username, String follower, int i) {
        try {
            if (isFollow(username, follower)) {
                String url = "UPDATE  follow SET status = ? WHERE username=? and follower=? and status = 0";
                PreparedStatement statement = connection.prepareStatement(url);
                statement.setInt(1, i);
                statement.setString(2, username);
                statement.setString(3, follower);
                statement.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unFollow(String username, String follower) {

        try {
            if (isFollowed(username, follower)) {
                String url = "DELETE FROM   follow WHERE username=? and follower=? ";
                PreparedStatement statement = connection.prepareStatement(url);

                statement.setString(1, username);
                statement.setString(2, follower);
                System.out.println(username + " " + follower);
                statement.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteTweet(int id, String username) {
        try {
            String url = "UPDATE post set content=null, reportid=null,link=null WHERE id=? and author=?";
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setInt(1, id);
            statement.setString(2, username);
            return statement.executeUpdate() != 0;
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void addAvatar(String imageBase64, String username) {
        try {
            String url = "UPDATE user SET avatar=? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(url);

            statement.setString(1, imageBase64);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
