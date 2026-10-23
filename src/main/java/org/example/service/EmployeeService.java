package org.example.service;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Сервис: логика приложения
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /** name - часть ФИО (может быть пустой), active - true/false или null (все). */
    public List<Employee> search(String name, Boolean active) {
        boolean hasName = name != null && !name.isBlank();
        if (hasName && active != null) {
            return employeeRepository.findByFullNameContainingIgnoreCaseAndActive(name.trim(), active);
        } else if (hasName) {
            return employeeRepository.findByFullNameContainingIgnoreCase(name.trim());
        } else if (active != null) {
            return employeeRepository.findByActive(active);
        }
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Сотрудник не найден: " + id));
    }

    public boolean emailExists(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
