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
 * @author Jett
 */
public class Reason implements Query {
    
    private age_income_box box;
    
    public Reason(age_income_box box){
        this.box = box;
    }
    
    
    public ResultSet go(){
        
        String queries = "select income_group, age_group,ynotsch as 'reason', count(age_yr) as 'number of answer' from\n" +
"(select `main.id`,\n" +
"(CASE\n" +
"when total_an_income BETWEEN 0 and 20000 THEN '0-20000'\n" +
box.getMoney()+
"when total_an_income >= 30002 THEN '30002 +' END) as income_group\n" +
"from (select `main.id`, sum(wagcshm+wagkndm) as total_an_income from hpq_tempmem group by `main.id`) as income_table    \n" +
") as income_perhh,\n" +
"\n" +
"(select `main.id`, age_yr,educind, ynotsch,\n" +
"(CASE\n" +
"WHEN age_yr BETWEEN 0 and 18 THEN '18 and below'\n" +
"WHEN age_yr >= 19 THEN '19 and above' END\n" +
") as age_group\n" +
"from hpq_tempmem\n" +
"where educind =2 and ynotsch!= \"\"\n" +
")as filter_mem\n" +
"where filter_mem.`main.id` = income_perhh.`main.id` and age_group is not null and income_group is not null\n" +
"group by ynotsch, age_group,income_group";
        
               Statement s= null;
       ResultSet rs = null;
        try {
            s = Data.con.createStatement();
            System.out.println(queries);
            s.execute(queries);
            rs = s.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
        
    }
    
    
    
}
