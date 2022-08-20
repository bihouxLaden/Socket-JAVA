/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employe;

import java.io.*;
import java.sql.*;
import java.net.*;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Miandrisoa Hoby
 */
public class Server extends Thread {
    
    Statement stmt = null;
    Vector records = new Vector(10,10);
    ResultSet rs = null;
    ServerSocket server = null;
    Socket client = null;
    Connection con = null;
    ObjectOutputStream out = null;
    String str = null;
    publisher pub = null;
    
    public Server()
    {
        try {
            server= new ServerSocket(1400);
            System.out.println("Demarrage du serveur");
            start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void run()
    
    {
        while (true) 
        {            
            try {
                int CC;
                client = server.accept();
                System.out.println("Connection accepté");
                out = new ObjectOutputStream(client.getOutputStream());
                System.out.println("OutputStream reçue");
                
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/sEmploye","root","");
                    stmt = con.createStatement();
                    rs = stmt.executeQuery("select * from employe");
                    records.removeAllElements();
                    
                    ResultSetMetaData RSMD = rs.getMetaData();
                    CC = RSMD.getColumnCount();
                    
                            while(rs.next())
                            {
                                pub = new publisher();
                                pub.numEmp = rs.getString(1);
                                pub.nom = rs.getString(2);
                                pub.adr = rs.getString(3);
                                pub.sal = rs.getString(4);
                                records.addElement(pub);
                                System.out.println("Ligne renvoyée");
                            }
                        out.writeObject(records);
                        out.close();
                        System.out.println("Chaîne renvoyée");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
 
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    public static void main(String args[])
{
   new Server();
 
}
}

