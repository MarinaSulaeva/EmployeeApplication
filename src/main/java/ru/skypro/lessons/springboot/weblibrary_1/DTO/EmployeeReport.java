package ru.skypro.lessons.springboot.weblibrary_1.DTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EmployeeReport implements Serializable {
    private int department;
    private Long countOfEmployees;
    private int minSalary;
    private int maxSalary;
    private double averageSalary;
}
