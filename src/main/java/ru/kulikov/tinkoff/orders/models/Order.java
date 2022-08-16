package ru.kulikov.tinkoff.orders.models;


import javax.validation.constraints.NotEmpty;


public class Order {

    @NotEmpty(message = "Category should not be empty")
    private String category;
    @NotEmpty(message = "Product name should not be empty")
    private String product_name;
    private int id;
    private int customer_id;

    public Order() {

    }

    public Order(String category, String product_name) {
        this.category = category;
        this.product_name = product_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
