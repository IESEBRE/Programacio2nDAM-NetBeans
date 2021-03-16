/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.beans.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import utilscontroller.Utils;
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
    public void loadTableFromArray() {
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
    
        //Mètode que carrega els objectes continguts a la col·lecció i els mostra a la JTable. La classe servix per indicar de quin tipo són els objectes de l'ArrayList (3r paràmetre)
    //Si volem que es puguen modificar les dades directament des de la taula hauríem d'usar el model instància de la classe ModelCanvisBD, que varia d'una BD a una altra
    //Al mètode usem el NonEditTableModel que no permet editar les cel·les de la JTable (el DefaultTableModel sí ho permet)
    //Esta versió afegix a la darrera columna de la taula l'objecte mostrat a la mateixa de manera que el podrem recuperar fàcilment per fer updates, deletes, etc...
    //Esta columna extra no apareix a la taula ja que la borrem, però la retornem per poder-la afegir quan sigue necessari
    public <E> TableColumn loadTableFromArray(E[] dades, Class<E> classe) {

        //variables locals
        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<Object[]> data = new ArrayList<>();
        //Per poder actualitzar la BD des de la taula usaríem el model comentat
        //ModelCanvisBD model;
        DefaultTableModel model;

        //Anotem el nº de camps de la classe
        Field[] camps = classe.getDeclaredFields();
        //Ordenem els camps alfabèticament
        Arrays.sort(camps, new Utils.OrderClassFieldsAlphabetically());
        int ncamps = camps.length;
        //Recorrem els camps de la classe i posem els seus noms com a columnes de la taula
        //Com hem hagut de posar _numero_ davant el nom dels camps, mostrem el nom a partir del 2n _ 
        for (Field f : camps) {
            columnNames.add(f.getName().substring(f.getName().lastIndexOf('_') + 1).toUpperCase());
        }
        //Afegixo al model de la taula una columna on guardaré l'objecte mostrat a cada fila (amago la columna al final per a que no aparegue a la vista)
        columnNames.add("objecte");
        //Si hi ha algun element a l'arraylist omplim la taula
        if (dades.length != 0) {
            Utils.<E>getTableData(dades, classe, columnNames, data, ncamps);

        }

        //Utilitzem el model que no permet editar les caselles de la taula
        model = new NotEditTableModel((Object[][]) data.toArray(new Object[0][]), columnNames.toArray());
        this.setModel(model);

        //Crido al mètode que configura les columnes de la taula, i retorna la columna que conté els objectes de la col·lecció
        return Utils.arrangeTableColumns(this);

    }

    
}
