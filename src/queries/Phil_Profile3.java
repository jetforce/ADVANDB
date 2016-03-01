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
public class Phil_Profile3 implements Query{

    private age_income_box box;    
    public Phil_Profile3(age_income_box box){
        this.box = box;
    }
    
    @Override
    public ResultSet go() {
        
        String query = "select mun, age_group,income_group,`type`,count(`age_group`) \n" +
        "from hpq_hh\n" +
        "join\n" +
        "(select `main.id`,(CASE\n" +
        "WHEN age_yr BETWEEN 18 and 28 THEN '18 to 28'\n" +
         box.getAge()+
        "WHEN age_yr >= 62 THEN '62 +' END) as age_group,memno\n" +
        "from hpq_mem_index) as filter\n" +
        "on filter.`main.id` = hpq_hh.`main.id`\n" +
        "\n" +
        "join \n" +
        "(select `main.id`, \n" +
        "(CASE\n" +
        "when total_an_income BETWEEN 0 and 20000 THEN '0-20000'\n" +
        box.getMoney()+
        "WHEN total_an_income >= 30002 THEN '30002 +' END) as income_group\n" +
        "from (select `main.id`, sum(wagcshm+wagkndm) as total_an_income from hpq_mem group by `main.id`) as income_table	\n" +
        ") as income_perhh \n" +
        "on income_perhh.`main.id` = filter.`main.id` \n" +
        "\n" +
        "left join (select `main.id`, mem_ref,`type` from all_phealth) as p_health\n" +
        "on p_health.`main.id` = filter.`main.id`\n" +
        "and p_health.mem_ref = filter.`memno`\n" +
        "\n" +
        "where age_group is not null and income_group is not null\n" +
        "group by mun, age_group, `type`,income_group";
        
        
        Statement s= null;
       ResultSet rs = null;
        try {
            s = Data.con.createStatement();
            System.out.println(query);
            s.execute(query);
            rs = s.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
        
        
    }
    
    
    
}
