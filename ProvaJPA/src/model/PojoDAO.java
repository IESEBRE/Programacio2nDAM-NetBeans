/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 *
 * @author profe
 */
public class PojoDAO {
    
    private EntityManagerFactory emf;
    private EntityManager em;

    public PojoDAO(String bd) {
        emf = Persistence.createEntityManagerFactory(bd);
        em = emf.createEntityManager();
    }

    
    public void createPojo(int x, int y) throws RollbackException{
        
        em.getTransaction().begin();
        Pojo p = new Pojo(x, y);
        em.persist(p);
        em.getTransaction().commit();
        
    }

    public void deletePojo(Pojo p) throws RollbackException{
        
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        
    }

    public void deletePojo(long id) throws RollbackException{
        
        em.getTransaction().begin();
        Pojo p=em.find(Pojo.class, id);
        em.remove(p);
        em.getTransaction().commit();
        
    }
    
    public void updatePojo(Pojo p, int nouX, int nouY) throws RollbackException{
        
        em.getTransaction().begin();
        p.setX(nouX);
        p.setY(nouY);
        em.getTransaction().commit();
        
    }

    public void updatePojo(long id, int nouX, int nouY) throws RollbackException{
        
        em.getTransaction().begin();
        Pojo p=em.find(Pojo.class, id);
        p.setX(nouX);
        p.setY(nouY);
        em.getTransaction().commit();
        
    }
    
    //Consultes vàries....



}
