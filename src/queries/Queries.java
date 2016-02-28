/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jet
 */
public class Queries {

    /**
     * @param args the command line arguments
     */
    
    public static ArrayList<Query> query_set = new ArrayList<>();
    
    public static void main(String[] args) {
       Frame f = new Frame();
       
       
       //Hannah, Rigel add here your query statement and Query UI. dapat in order.
       QueryBox1 box1 = new QueryBox1();
       f.addPanel("PWD", box1);
       query_set.add(new PWD_Query(box1));
       
       
       f.setVisible(true);
       System.out.println("Hello");
    }
    
}
