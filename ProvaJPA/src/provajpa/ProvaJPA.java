/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provajpa;

import model.Pojo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author profe
 */
public class ProvaJPA {

    public static void main(String[] args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("PROJECTE");
        EntityManager em = emf.createEntityManager();

        // Delete all Pojo objects in the database:
        em.getTransaction().begin();
        Query q0 = em.createQuery("DELETE FROM Pojo p");
        q0.executeUpdate();
        em.getTransaction().commit();
        

        // Store 1000 Pojo objects in the database:
        em.getTransaction().begin();
        for (int i = 0; i < 1000; i++) {
            Pojo p = new Pojo(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();

        // Find the number of Pojo objects in the database:
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Pojo p");
        System.out.println("Total Pojos: " + q1.getSingleResult());

        // Find the average X value:
        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Pojo p");
        System.out.println("Average X: " + q2.getSingleResult());

        // Retrieve all the Pojo objects from the database:
        TypedQuery<Pojo> query
                = em.createQuery("SELECT p FROM Pojo p", Pojo.class);
        List<Pojo> results = query.getResultList();
        for (Pojo p : results) {
            System.out.println(p);
        }
        
        // Find and modify some Pojo object:
        em.getTransaction().begin();
        Pojo p=em.find( Pojo.class, 201L );
        System.out.println("Before: " + p);
        p.setX(1201);
        p.setY(1201);
        em.getTransaction().commit();
        p=em.find( Pojo.class, 201L );
        System.out.println("After: " + p);

        // Find and delete some Pojo object:
        em.getTransaction().begin();
        p=em.find( Pojo.class, 201L );
        System.out.println("Before: " + p);
        em.remove(p);
        em.getTransaction().commit();
        p=em.find( Pojo.class, 201L );
        System.out.println("After: " + p);


        // Close the database connection:
        em.close();
        emf.close();
    }

}
