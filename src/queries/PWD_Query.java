/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jet
 */
public class PWD_Query implements Query{
    
    private QueryBox1 box;
    
    public PWD_Query(QueryBox1 ui){
        this.box = ui;
    }
    
    
    public ResultSet go(){
       Statement s= null;
       ResultSet rs = null;
        try {
            s = Data.con.createStatement();
            s.execute("select `main.id` from hpq_hh");
            rs = s.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    
    
}
