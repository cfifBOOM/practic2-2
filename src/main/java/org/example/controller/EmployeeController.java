package org.example.controller;

import org.example.model.Employee;
import org.example.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// Контроллер: принимает HTTP-запросы
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // status: all / active / inactive
    @GetMapping
    public String list(@RequestParam(required = false) String name,
                       @RequestParam(defaultValue = "all") String status,
                       Model model) {
        Boolean active = null;
        if (status.equals("active")) active = true;
        if (status.equals("inactive")) active = false;

        model.addAttribute("employees", employeeService.search(name, active));
        model.addAttribute("name", name);
        model.addAttribute("status", status);
        return "employees-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("action", "/employees/add");
        return "employee-form";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("employee") Employee employee,
                      BindingResult result, Model model) {
        if (!result.hasFieldErrors("email") && employeeService.emailExists(employee.getEmail())) {
            result.rejectValue("email", "duplicate", "Сотрудник с таким email уже существует");
        }
        if (result.hasErrors()) {
            model.addAttribute("action", "/employees/add");
            return "employee-form";
        }
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getById(id));
        model.addAttribute("action", "/employees/edit/" + id);
        return "employee-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("employee") Employee employee,
                       BindingResult result, Model model) {
        Employee old = employeeService.getById(id);
        if (!result.hasFieldErrors("email")
                && !old.getEmail().equals(employee.getEmail())
                && employeeService.emailExists(employee.getEmail())) {
            result.rejectValue("email", "duplicate", "Сотрудник с таким email уже существует");
        }
        if (result.hasErrors()) {
            model.addAttribute("action", "/employees/edit/" + id);
            return "employee-form";
        }
        employee.setId(id);
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }
}
