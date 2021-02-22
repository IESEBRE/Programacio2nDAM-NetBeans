/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author profe
 */
@Entity
@Table(name = "POJO")
public class Pojo implements Serializable{
    
    
    private static final long serialVersionUID = 1L;
    
    //@Id @GeneratedValue
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "X")
    private int x;
    @Column(name = "Y")
    private int y;

    public Pojo() {
    }

    public Pojo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public int getX() {
         return x;
    }

    public int getY() {
         return y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }
}
