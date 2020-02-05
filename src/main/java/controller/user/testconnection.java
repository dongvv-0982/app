/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

/**
 *
 * @author root
 */
public class testconnection {

    public static void main(String[] args) {
        try {
            String url = "https://www.facebook.com";
            String command
                    = "curl -X GET " + url;
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.directory(new File("/home/"));
            Process process = processBuilder.start();

            Scanner s = new Scanner(process.getInputStream()).useDelimiter("\\A");
            String result = "";
            while(s.hasNext()){
                result += s.next();
            }
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            System.out.println(result);

        } catch (IOException ex) {
            Logger.getLogger(testconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void testIt() {

        String https_url = "https://www.google.com/";
        URL url;
        try {

            url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            //dumpl all cert info
//	     print_https_cert(con);
            //dump all the content
            print_content(con);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void print_https_cert(HttpsURLConnection con) {

        if (con != null) {

            try {

                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for (Certificate cert : certs) {
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void print_content(HttpsURLConnection con) {
        if (con != null) {

            try {

                System.out.println("****** Content of the URL ********");
                BufferedReader br
                        = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));

                String input;

                while ((input = br.readLine()) != null) {
                    System.out.println(input);
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
