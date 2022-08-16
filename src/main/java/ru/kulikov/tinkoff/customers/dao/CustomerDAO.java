package ru.kulikov.tinkoff.customers.dao;

import org.springframework.stereotype.Component;
import ru.kulikov.tinkoff.customers.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class CustomerDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/tinkoff";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "mk11072002";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Customer> index() {
        List<Customer> customers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM customers";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Customer customer = new Customer();

                customer.setId(resultSet.getInt("customer_id"));
                customer.setName(resultSet.getString("name"));
                customer.setSurname(resultSet.getString("surname"));
                customer.setCity(resultSet.getString("city"));

                customers.add(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customers;
    }

    public Customer show(int id) {
        Customer customer = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM customers WHERE customer_id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            customer = new Customer();

            customer.setId(resultSet.getInt("customer_id"));
            customer.setName(resultSet.getString("name"));
            customer.setSurname(resultSet.getString("surname"));
            customer.setCity(resultSet.getString("city"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customer;
    }

    public void save(Customer customer) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO customers VALUES(?, ?, ?)");

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getCity());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Customer updatedCustomer) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE customers SET name=?, surname=?, city=? WHERE customer_id=?");

            preparedStatement.setString(1, updatedCustomer.getName());
            preparedStatement.setString(2, updatedCustomer.getSurname());
            preparedStatement.setString(3, updatedCustomer.getCity());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM customers WHERE customer_id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
