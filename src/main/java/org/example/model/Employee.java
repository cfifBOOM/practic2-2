package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

// Модель: этот класс - таблица в базе данных
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ФИО не может быть пустым")
    @Size(min = 3, max = 100, message = "ФИО должно быть от 3 до 100 символов")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный формат email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Телефон не может быть пустым")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Телефон должен содержать 10-15 цифр, можно с + в начале")
    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "Должность не может быть пустой")
    @Column(name = "position")
    private String position;

    @NotNull(message = "Зарплата обязательна")
    @DecimalMin(value = "0.01", message = "Зарплата должна быть больше 0")
    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "active")
    private boolean active = true;

    public Employee() {}

    public Employee(String fullName, String email, String phone, String position, BigDecimal salary, boolean active) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
        this.active = active;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
