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
import javax.swing.JCheckBox;

/**
 *
 * @author Jet
 */
public class Voter_Profile implements Query{

    private age_income_box box;
    
    private ArrayList<JCheckBox> num_list;
    
    public Voter_Profile(age_income_box box){
        this.box=  box;
        
        
    }
    
    
    @Override
    public ResultSet go() {
        
        String query = "SELECT age_group, income_group, sex,educal, count(*) " +
                        "FROM " +
                        "(select `main.id`, sex,age_yr,educal,regvotind, " +
                        "(CASE " +
                        "WHEN age_yr BETWEEN 18 and 28 THEN '18 to 28' " +
                        this.box.getAge()+
                        "WHEN age_yr >= 62 THEN '62 +' END) as age_group  " +
                        "from hpq_mem  " +
                        "where regvotind = 1) as filter_mem, " +
                        "(select `main.id`, " +
                        "(CASE " +
                        "when total_an_income BETWEEN 0 and 20000 THEN '0-20000' " +
                        this.box.getMoney() +
                        "WHEN total_an_income >= 30002 THEN '30002 +' END) as income_group " +
                        "from (select `main.id`, sum(wagcshm+wagkndm) as total_an_income from hpq_mem group by `main.id`) as income_table " +
                        ") as income_perhh  " +
                        "where filter_mem.`main.id` = income_perhh.`main.id` and age_group is not null and income_group is not null " +
                        "group by educal, sex, age_group,income_group";
        
       System.out.println(query);                 
       Statement s= null;
       ResultSet rs = null;
        try {
            s = Data.con.createStatement();
       
            s.execute(query);
            rs = s.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
                        
                      
    }
    
    
    
}
