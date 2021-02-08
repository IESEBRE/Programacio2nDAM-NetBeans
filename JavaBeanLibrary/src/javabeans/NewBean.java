/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabeans;

import java.beans.*;
import java.io.Serializable;
import java.util.Properties;
import java.beans.VetoableChangeListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author profe
 */
public class NewBean implements Serializable, VetoableChangeListener {

    private Connection conn = null;
    
    private String propsDB;

    public static final String PROP_PROPSDB = "propsDB";

    public String getPropsDB() {
        return propsDB;
    }

    public void setPropsDB(String propsDB) throws PropertyVetoException {
        String oldPropsDB = this.propsDB;
        vetoableChangeSupport.fireVetoableChange(PROP_PROPSDB, oldPropsDB, propsDB);
        this.propsDB = propsDB;

    }

    private transient final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.removeVetoableChangeListener(listener);
    }

    
    public NewBean() {
        this.addVetoableChangeListener(this);
    }

    
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        switch (evt.getPropertyName()) {
            case NewBean.PROP_PROPSDB:
                Properties properties = new Properties();
                try {
                    properties.load(new FileInputStream((String) evt.getNewValue()));
                    String url = (String) properties.get("url");
                    String user = (String) properties.get("usuari");
                    String pass = (String) properties.get("password");
                    conn = DriverManager.getConnection(url, user, pass);
                    System.out.println("Conectat en exit");
                    JOptionPane.showMessageDialog(null, "Connexió establerta!!");

                } catch (IOException | SQLException ex) {
                    throw new PropertyVetoException("", evt);
                }
                finally{
                    if(conn!=null) try {
                        conn.close();
                    } catch (SQLException ex) {
                        throw new PropertyVetoException("", evt);
                    }
                }
                break;
            default:
                System.out.println("Propietat restringida no tractada!!");
                JOptionPane.showMessageDialog(null, evt);
        }
    }

}
