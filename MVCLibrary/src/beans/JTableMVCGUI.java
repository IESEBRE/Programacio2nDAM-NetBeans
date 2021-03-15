/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.beans.*;
import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static utilscontroller.Utils.NotEditTableModel;

/**
 *
 * @author profe
 */
public class JTableMVCGUI extends javax.swing.JTable implements Serializable {
    
    
    private Object[][] dades=new Object[0][];
    
    private String[] nomCols=new String[0];

    public String[] getNomCols() {
        return nomCols;
    }

    public void setNomCols(String[] nomCols) {
        this.nomCols = nomCols;
    }

    public Object[][] getDades() {
        return dades;
    }

    public void setDades(Object[][] dades) {
        this.dades = dades;
    }
    
    public JTableMVCGUI() {
        super();
    }
    
      //Passem el nom de columnes i les dades en arrays
    //Les cel·les de la JTable no es podran editar
    public void loadTable() {
        //Variables locals
        DefaultTableModel model;

        //Mirem si han passat columnes i dades. En cas contrari sortim
        if (nomCols == null || dades == null) {
            return;
        }


        //model = new NotEditTableModel(data, columnNames);
        model = new NotEditTableModel(dades, nomCols);
        this.setModel(model);

        //Fico la següent instrucció per a que s'ompligue tota la taula en els columnes
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        for (int i = 0; i < this.getColumnCount(); i++) {
            this.getColumnModel().getColumn(i).setPreferredWidth(250);
        }
    }
    
}
