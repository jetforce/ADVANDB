/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    
    
    public String getQuery(){
        ArrayList<Integer> muns = box.getNums();
        if(muns.isEmpty()){
            return "";
        }
        String query =  "where mun = "+muns.get(0)+" ";
        for(int i=1;i< muns.size(); i++){
            query = query +"or mun = "+muns.get(i)+" ";
        }
        return query;
    }
    
    public ResultSet go(){
       Statement s= null;
       ResultSet rs = null;
        try {
            s = Data.con.createStatement();
            System.out.println("select 	t1.mun , `pwd_type` , count(pwd_type) " +
                        "from (select `main.id`,mun from hpq_hh "+this.getQuery()+") as t1, (select `main.id`, `pwd_type` from hpq_mem where `pwd_type` != '' ) as t2 " +
                        "where `t1`.`main.id` = `t2`.`main.id`" +
                        "group by t1.`mun`,`t2`.`pwd_type`");
            s.execute("select 	t1.mun , `pwd_type` , count(pwd_type) " +
                        "from (select `main.id`,mun from hpq_hh "+this.getQuery()+") as t1, (select `main.id`, `pwd_type` from hpq_mem where `pwd_type` != '' ) as t2 " +
                        "where `t1`.`main.id` = `t2`.`main.id`" +
                        "group by t1.`mun`,`t2`.`pwd_type`");
            rs = s.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    
    
}
