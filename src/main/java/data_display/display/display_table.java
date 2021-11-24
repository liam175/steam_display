package data_display.display;

import javax.swing.*;
import javax.swing.table.TableColumn;

import data_display.file_things.get_data;

public class display_table {
    public JTable table = new JTable(12,1);
    public void resetTable(get_data data, int team){
        Object vals[] = data.getDataTable(team);
        for(int i = 0;i<12;i++){
            try{
               table.setValueAt(vals[i],i,0); 
            }catch(ArrayIndexOutOfBoundsException e){
                
            }
            
        }
        
    }
}
