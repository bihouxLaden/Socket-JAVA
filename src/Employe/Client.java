package Employe;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
 
public class Client extends JFrame {
    
    String str;
    ResultSet rs;
    Vector records;
    GridBagLayout gb1;
    GridBagConstraints gbc;
    JScrollPane sp;
    JTextArea result;
    JLabel label;
    publisher pub;
    int i=0;
    ObjectInputStream br = null;
    Socket clientSocket = null;
    
    public Client()
    {
        label = new JLabel("Details Produits");
        result = new JTextArea(20,60);
        str = "";
        pub = null;
        records = new Vector();
        gb1 = new GridBagLayout();
        gbc = new GridBagConstraints();
        getContentPane().setLayout(gb1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(label,gbc);
          gbc.gridx = 0;
        gbc.gridy = 1;
        sp = new JScrollPane(result);
        getContentPane().add(sp,gbc);
        
        
  
            try {
                clientSocket = new Socket("localhost",1400);
                  br = new ObjectInputStream(clientSocket.getInputStream());
                  records =(Vector)br.readObject();
                  br.close();
                  result.setText("");
                  int i =0;
                  
                  result.append("NumEmploye\tNom\tAdr\tSalaire");
                  result.append("\n-----------------------------------------------------------------------\n");
                  
                  
                  while(i < records.size())
                  {
                      pub = (publisher)records.elementAt(i);
                      str = pub.numEmp;
                      result.append(str + "\t");
                         str = pub.nom;
                      result.append(str + "\t");
                           str = pub.adr;
                      result.append(str + "\t");
                             str = pub.sal;
                      result.append(str + "\n");
                      i++;
                      
                      
                  }
                  records.removeAllElements();
                  
                  
                    
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
        
        
        
    }
    public static void main(String[ ] args)
{
Client server1=new Client();
      
                server1.setSize(500, 300);
                server1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                server1.pack();
                server1.setVisible(true);
                
     }
}