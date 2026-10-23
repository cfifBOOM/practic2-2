package org.example.repository;

import org.example.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Репозиторий: работа с базой данных (готовые методы save, findAll и др.)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    List<Employee> findByFullNameContainingIgnoreCase(String name);

    List<Employee> findByActive(boolean active);

    List<Employee> findByFullNameContainingIgnoreCaseAndActive(String name, boolean active);
}
