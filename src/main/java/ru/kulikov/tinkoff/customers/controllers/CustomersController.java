package ru.kulikov.tinkoff.customers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kulikov.tinkoff.customers.dao.CustomerDAO;
import ru.kulikov.tinkoff.customers.models.Customer;
import ru.kulikov.tinkoff.orders.dao.OrderDAO;

import javax.validation.Valid;


@Controller
@RequestMapping("/customers")
public class CustomersController{

    private final CustomerDAO customerDAO;
    private final OrderDAO orderDAO;

    @Autowired
    public CustomersController(CustomerDAO personDAO, OrderDAO orderDAO) {
        this.customerDAO = personDAO;
        this.orderDAO = orderDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("customers", customerDAO.index());
        return "customers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("customer", customerDAO.show(id));
        model.addAttribute("orders", orderDAO.showOrderList(id));
        return "customers/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("customer") Customer customer) {
        return "customers/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("customer") @Valid Customer customer,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "customers/new";

        customerDAO.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("customer", customerDAO.show(id));
        return "customers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "customers/edit";

        customerDAO.update(id, customer);
        return "redirect:/customers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        customerDAO.delete(id);
        return "redirect:/customers";
    }
}
