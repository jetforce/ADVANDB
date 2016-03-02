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
 * @author Hannah
 */
public class CBEP_Query implements Query {
    
    private CBEP_Age_Box ageBox;
    
    public CBEP_Query(CBEP_Age_Box ageBox){
        this.ageBox = ageBox;
    }
    
    public String getAgeSpan(){
        int span1 = ageBox.getSpan1();
        int span2 = ageBox.getSpan2();
        if(span2 > span1){
            return "";
        }
        else
            return "where age >= " + span1 + " and age <= " + span2 + " ";
       
    }
    
    public ResultSet go(){
       Statement s= null;
       ResultSet rs = null;
       String query;
        try {
            query = "select 'All' as 'Municipality', \n" +
                    "	( case \n" +
                    "    when m.jstatus = 1 then 'Permanent' \n" +
                    "    when m.jstatus = 2 then 'Short-term, seasonal or casual' \n" +
                    "    when m.jstatus = 3 then 'Worked on different jobs on day to day or week to week'\n" +
                    "    else 'No Job Status' \n" +
                    "    end ) as 'Job Status', \n" +
                    "    count(m.id) as 'Number of CBEP Beneficiaries', avg(wagcshm + wagkndm) as 'Avg Yearly Income'\n" +
                    "from ( select concat(`main.id`, memno) as id, jstatus, age_yr, wagcshm, wagkndm from hpq_mem\n" +
                            getAgeSpan() +  " ) as m,\n" +
                    "	 ( select `main.id`, mun from hpq_hh) as h,\n" +
                    "	 ( select `main.id`, concat(`main.id`, cbep_mem_refno) as id from hpq_cbep_mem ) as c\n" +
                    "where  h.`main.id` = c.`main.id` and m.id = c.id\n" +
                    "group by m.jstatus asc\n" +
                    "\n" +
                    "union\n" +
                    "\n" +
                    "select h.mun as 'Municipality',\n" +
                    "	( case \n" +
                    "    when m.jstatus = 1 then 'Permanent' \n" +
                    "    when m.jstatus = 2 then 'Short-term, seasonal or casual' \n" +
                    "    when m.jstatus = 3 then 'Worked on different jobs on day to day or week to week' \n" +
                    "    else 'No Job Status' \n" +
                    "    end ) as 'Job Status', \n" +
                    "	count(m.id) as 'Number of CBEP Beneficiaries', avg(wagcshm + wagkndm) as 'Avg Yearly Income'\n" +
                    "from ( select concat(`main.id`, memno) as id, jstatus, age_yr, wagcshm, wagkndm from hpq_mem\n" +
                            getAgeSpan() +  " ) as m,\n" +
                    "	 ( select `main.id`, mun from hpq_hh) as h,\n" +
                    "	 ( select `main.id`, concat(`main.id`, cbep_mem_refno) as id from hpq_cbep_mem ) as c\n" +
                    "where  h.`main.id` = c.`main.id` and m.id = c.id\n" +
                    "group by h.mun asc, m.jstatus asc;";
            s = Data.con.createStatement();
            s.execute(query);
            rs = s.getResultSet();
            System.out.println(query);
        } catch (SQLException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
