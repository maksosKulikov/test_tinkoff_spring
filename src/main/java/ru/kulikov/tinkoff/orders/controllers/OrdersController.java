package ru.kulikov.tinkoff.orders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kulikov.tinkoff.customers.dao.CustomerDAO;
import ru.kulikov.tinkoff.orders.dao.OrderDAO;
import ru.kulikov.tinkoff.orders.models.Order;

import javax.validation.Valid;


@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final CustomerDAO customerDAO;
    private final OrderDAO orderDAO;

    @Autowired
    public OrdersController(CustomerDAO customerDAO, OrderDAO orderDAO) {
        this.customerDAO = customerDAO;
        this.orderDAO = orderDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("orders", orderDAO.index());
        return "orders/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", orderDAO.show(id));
        model.addAttribute("customer", customerDAO.show(orderDAO.show(id).getCustomer_id()));
        return "orders/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("order") Order order, Model model) {
        model.addAttribute("customers", customerDAO.index());
        return "orders/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("order") @Valid Order order,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "orders/new";

        orderDAO.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("order", orderDAO.show(id));
        model.addAttribute("customers", customerDAO.index());
        return "orders/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "orders/edit";

        orderDAO.update(id, order);
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        orderDAO.delete(id);
        return "redirect:/orders";
    }
}
