/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author Steven
 */
public class Person {

    private int id;
    private String name;
    private String lastname1;
    private String lastname2;
    private String bornDate;
    private String country;
    private int fatherId;

    public Person() {
    }

    public Person(int id, String name, String lastname1, String astname2, String bornDate, String country, int fatherId) {
        this.id = id;
        this.name = name;
        this.lastname1 = lastname1;
        this.lastname2 = astname2;
        this.bornDate = bornDate;
        this.country = country;
        this.fatherId = fatherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname1() {
        return lastname1;
    }

    public void setLastname1(String lastname1) {
        this.lastname1 = lastname1;
    }

    public String getLastname2() {
        return lastname2;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getFatherId() {
        return fatherId;
    }

    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", lastname1=" + lastname1 + ", astname2=" + lastname2 + ", bornDate=" + bornDate + ", country=" + country + ", fatherId=" + fatherId + '}';
    }

    
}
