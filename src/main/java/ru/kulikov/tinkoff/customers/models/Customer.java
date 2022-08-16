package ru.kulikov.tinkoff.customers.models;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class Customer {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 50, message = "Surname should be between 2 and 50 characters")
    private String surname;

    @NotEmpty(message = "City should not be empty")
    private String city;

    private int id;

    public Customer() {

    }

    public Customer(String name, String surname, String city) {
        this.name = name;
        this.surname = surname;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
