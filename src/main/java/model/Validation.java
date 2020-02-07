/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dal.ControlDAO;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class Validation {
    private ControlDAO db = new ControlDAO();
    String charset = "abcdefghijklmnopqrstuvwxyzABCDYFGHIJKLMNOPQRSTUVWXYZ_0123456789@.!*^&$";
    private HashMap<Character, String> alter = new HashMap<>();
    public Validation() {
        
        alter.put('&', "&amp");
        alter.put('<', "&lt");
        alter.put('"', "&quot");
        alter.put('\'', "&#x27");
//        alter.put('/', "&#x2F");
        
    }
    public String validate(String text){
        if(text == null) return "";
        for (Character character : alter.keySet()) {
            text = text.replaceAll(character+"", alter.get(character));
        }
        return text;
    }
    
    public boolean charset(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!charset.contains(text.charAt(i) + "")) {
                return false;
            }
        }
        return true;
    }

    public boolean isUsername(String username) {
        return db.getRole(username) != null;
    }

    public boolean isTweetId(int id) {
        return db.checkid(id);
    }

    public boolean isTypeId(int typeid) {
        return ControlDAO.reportTypes.get(typeid) != null;
    }

    public boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(email).matches();
    }

    public boolean isPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!containLowerCase(password)) {
            return false;
        }
        if (!containUpperCase(password)) {
            return false;
        }
        if (!containSpectialChar(password)) {
            return false;
        }
        return true;
    }

    private boolean containLowerCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) <= 'z' && password.charAt(i) >= 'a') {
                return true;
            }
        }
        return false;
    }

    private boolean containUpperCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) <='Z' && password.charAt(i) >= 'A') {
                return true;
            }
        }
        return false;
    }

    private boolean containSpectialChar(String password) {
        for (int i = 0; i < password.length(); i++) {
            if ("!@#$%^&*".contains(password.charAt(i) +"")) {
                return true;
            }
        }
        return false;

    }
}
