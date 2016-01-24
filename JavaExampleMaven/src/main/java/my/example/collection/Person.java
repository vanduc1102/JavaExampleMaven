/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.example.collection;

import java.util.Date;

/**
 *
 * @author ducnguyen
 */
public class Person {
    private int id;
    private String name;
    private String address;
    private Date birtDate;
    
    public Person() {
    
    }
    
    public Person(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the birtDate
     */
    public Date getBirtDate() {
        return birtDate;
    }

    /**
     * @param birtDate the birtDate to set
     */
    public void setBirtDate(Date birtDate) {
        this.birtDate = birtDate;
    }

    @Override
    public String toString() {
        return "Person[id="+this.id+";name="+this.name+";address="+this.address+"]"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
