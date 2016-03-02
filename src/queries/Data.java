/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jet
 */
public class Data {
    
    public static Connection con = Data.getConnection("root","0987");
    
    private static Connection getConnection(String user,String password){
         try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("Where is your MySQL JDBC Driver?");
		e.printStackTrace();
		
	}

	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;

	try {
                 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+"Marinduque",user,password);
                 //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+"poverty",user,password);
                //connection = DriverManager.getConnection("jdbc:mysql://localhost/?user="+user+"&password="+password);    
	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}

	if (connection != null) {
		System.out.println("You made it, take control your database now!");
	} else {
		System.out.println("Failed to make connection!");
	}
        return connection;
    }
    
}
