package trionabackend.trionatestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int phonenumber;
    private int countrycode;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    private String address;


    public void setCountrycode (int countrycode)
    {
        this.countrycode = countrycode;
    }

    public int getCountrycode ()
    {
        return countrycode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhonenumber(int phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public int getPhonenumber()
    {
        return phonenumber;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

}