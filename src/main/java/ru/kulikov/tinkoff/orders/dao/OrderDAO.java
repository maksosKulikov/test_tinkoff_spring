package ru.kulikov.tinkoff.orders.dao;

import org.springframework.stereotype.Component;
import ru.kulikov.tinkoff.customers.models.Customer;
import ru.kulikov.tinkoff.orders.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class OrderDAO {

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

    public List<Order> index() {
        List<Order> orders = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM orders";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Order order = new Order();

                order.setId(resultSet.getInt("order_id"));
                order.setCategory(resultSet.getString("category"));
                order.setProduct_name(resultSet.getString("product_name"));

                orders.add(order);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return orders;
    }

    public Order show(int id) {
        Order order = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM orders WHERE order_id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            order = new Order();

            order.setId(resultSet.getInt("order_id"));
            order.setCategory(resultSet.getString("category"));
            order.setProduct_name(resultSet.getString("product_name"));
            order.setCustomer_id(resultSet.getInt("customer_id"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return order;
    }

    public void save(Order order) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO orders VALUES(?, ?, ?)");

            preparedStatement.setString(1, order.getCategory());
            preparedStatement.setString(2, order.getProduct_name());
            preparedStatement.setInt(3, order.getCustomer_id());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Order updatedOrder) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE orders SET category=?, product_name=?, customer_id=? WHERE order_id=?");

            preparedStatement.setString(1, updatedOrder.getCategory());
            preparedStatement.setString(2, updatedOrder.getProduct_name());
            preparedStatement.setInt(3, updatedOrder.getCustomer_id());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM orders WHERE order_id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Order> showOrderList(int id) {
        List<Order> orders = new ArrayList<>();

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM orders WHERE customer_id = ?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();

                order.setId(resultSet.getInt("order_id"));
                order.setCategory(resultSet.getString("category"));
                order.setProduct_name(resultSet.getString("product_name"));

                orders.add(order);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return orders;
    }
}
