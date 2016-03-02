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
public class Poverty_Query implements Query {
    
    private QueryBox1 checkBoxes;
    
    public Poverty_Query(QueryBox1 box){
        this.checkBoxes = box;
    }
    
    public String getMunicipalities(){
        
        ArrayList<Integer> municipalities = checkBoxes.getNums();
        
        if(municipalities.isEmpty()){
            return "";
        }
        
        String query =  "where mun = "+municipalities.get(0)+" ";
        
        for(int i=1;i< municipalities.size(); i++){
            query = query + "or mun = " + municipalities.get(i) + " ";
        }
        
        return query;
        
    }
    
    public ResultSet go(){
       Statement s= null;
       ResultSet rs = null;
       String query;
        try {
            query = "select m.`main.id` as 'Household ID', \n" +
                    "	`Number of Members`, `Avg Income in the Year`, `Target Avg Income for a Year`\n" +
                    "from\n" +
                    "	( select `main.id`, count(memno) as 'Number of Members', \n" +
                    "     avg(wagcshm + wagkndm) as 'Avg Income in the Year',\n" +
                    "	  (19252.8 * count(memno)) as 'Target Avg Income for a Year'\n" +
                    "     from hpq_mem\n" +
                    "     group by `main.id`\n" +
                    "     having avg(wagcshm + wagkndm) < (19252.8 * count(memno))) as m,\n" +
                    "     ( select `main.id`, mun\n" +
                    "     from hpq_hh \n" + getMunicipalities() +
                    "      ) as h\n" +
                    "where m.`main.id` = h.`main.id`;";
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
